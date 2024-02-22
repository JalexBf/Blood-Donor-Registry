package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Roles;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.*;
import gr.hua.dit.ds.BloodRegistry.exceptions.CustomException;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class SecretariatService {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecretariatRepository secretariatRepository;


    @PreAuthorize("hasAuthority('ROLE_SECRETARIAT')")
    public List<Application> getPendingApplications() {
        return applicationService.findAllApplicationsByStatus(Status.AWAITING);
    }


    @PreAuthorize("hasAuthority('ROLE_SECRETARIAT')")
    public void updateApplicationDecision(Long applicationId, Status status, Long secretariatId) {
        // Fetch the secretariat user based on the ID
        User secretariat = userRepository.findById(secretariatId)
                .orElseThrow(() -> new NotFoundException("Secretariat not found with id: " + secretariatId));

        // Check if the user has the secretariat role
        boolean isSecretariat = secretariat.getRoles().stream()
                .anyMatch(role -> role.getName().equals(Roles.ROLE_SECRETARIAT.name()));

        if (!isSecretariat) {
            throw new CustomException("User with id: " + secretariatId + " does not have secretariat permissions.");
        }

        // Proceed to update the application status
        applicationService.updateApplicationStatus(applicationId, status);
    }



    // For scalability purposes
    public Optional<Secretariat> findSecretariatById(Long id) {
        return secretariatRepository.findById(id);
    }


    public List<Secretariat> findAllSecretariats() {
        return secretariatRepository.findAll();
    }

}
