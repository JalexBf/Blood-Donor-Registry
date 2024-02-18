package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;


@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public Registration createRegistration(Registration registration) {
        return registrationRepository.save(registration);
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
        Registration savedRegistration = registrationRepository.save(registration);

        // Update the applicant's role to BLOOD_DONOR upon approval
        userService.updateUserRole(registration.getBloodDonor().getUsername(), "BLOOD_DONOR");

        return savedRegistration;
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
