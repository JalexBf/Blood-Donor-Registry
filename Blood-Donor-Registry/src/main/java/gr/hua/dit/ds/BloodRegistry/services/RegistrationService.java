package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.DTO.RegistrationdDto;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secretariat;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.BloodDonorRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.RegistrationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    BloodDonorRepository bloodDonorRepository;
    @Autowired
    SecreteriatService secreteriatService;

    @Autowired
    JavaMailSender mailSender;

    @Transactional
    @PreAuthorize("hasAuthority('ROLE_BLOOD_DONOR')")
    public Registration createRegistration(RegistrationdDto registrationDto) {
        BloodDonor bloodDonor = bloodDonorRepository.findByUserId(registrationDto.getId())
                .orElseThrow(() -> new RuntimeException("Blood Donor not found"));

        // Check if a registration already exists
        Optional<Registration> existingRegistration = registrationRepository.findByBloodDonor(bloodDonor);
        if (existingRegistration.isPresent()) {
            // User has already registered
            throw new IllegalStateException("Registration already exists for this user");
        }

        Registration registration = new Registration();
        registration.setBloodDonor(bloodDonor);
        registration.setSubmissionDate(LocalDate.now());
        registration.setStatus(Status.AWAITING);
        return registrationRepository.saveAndFlush(registration);
    }


    public BloodDonor findBloodDonorByRegistrationId(Long registrationId) {
        // Fetch the registration object by ID
        Registration registration = registrationRepository.findById(registrationId).orElse(null);

        if (registration != null) {
            // Return the associated BloodDonor directly
            return registration.getBloodDonor();
        } else {
            // Return null if the registration is not found
            return null;
        }
    }

    public User findUserByRegistrationId(Long registrationId) {
        BloodDonor bloodDonor = findBloodDonorByRegistrationId(registrationId);
        if (bloodDonor != null) {
            // Assuming BloodDonor is a subclass of User, or can be cast to User safely
            return (User) bloodDonor;
        } else {
            // Return null if no BloodDonor/User is found
            return null;
        }
    }

    public void sendEmail(String recipientEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom("dimopthan@gmail.com", "Blood Donation Secretary");
            helper.setTo(recipientEmail);

            String subject = "Κέντρο Αιμοδοσίας";

            String content = "<p>Γεια σας,</p>"
                    + "<p>Σύμφωνα με τα στοιχεία τα οποία εγγραφήακτε στην εφαρμογή μας, διαπιστώθηκε ότι μπορείτε να γίνεται αιμοδότης!</p>"
                    + "<p>Παρακαλώ επικοινωνήστε μαζί μας για περισσότερες πληροφορίες.</p>";
            helper.setSubject(subject);

            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    @Transactional
    public void deleteRegistration(Long registrationId) {
        registrationRepository.deleteById(registrationId);
    }


    @PreAuthorize("hasAuthority('ROLE_SECRETARIAT')")
    @Transactional
    public Registration approveRegistration(Long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new NotFoundException("Registration not found with id: " + registrationId));
        registration.setStatus(Status.APPROVED);
        return registrationRepository.save(registration);
    }


    @PreAuthorize("hasAuthority('ROLE_SECRETARIAT')")
    @Transactional
    public Registration rejectRegistration(Long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new NotFoundException("Registration not found with id: " + registrationId));
        registration.setStatus(Status.REJECTED);
        return registrationRepository.save(registration);
    }


    public Registration findRegistrationById(Long registrationId) {
        return registrationRepository.findById(registrationId)
                .orElseThrow(() -> new NotFoundException("Registration not found with id: " + registrationId));
    }

    public List<Registration> findAllRegistrations() {
        return registrationRepository.findAll();
    }


    public List<Registration> findAllRegistrationsByStatus(Status status) {
        return registrationRepository.findByStatus(status);
    }

}
