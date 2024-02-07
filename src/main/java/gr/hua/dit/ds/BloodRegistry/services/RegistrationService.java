package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.DTO.RegistrationdDto;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secretariat;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.BloodDonorRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

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
