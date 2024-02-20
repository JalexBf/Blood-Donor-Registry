package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Roles;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.Application;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.exceptions.CustomException;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.ApplicationRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.BloodDonorRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BloodDonorRepository bloodDonorRepository;


    @PreAuthorize("hasAuthority('CITIZEN')")
    @Transactional
    public Application createApplication(Application application) {
        // Check for existing application by AMKA
        if (bloodDonorRepository.existsByAmka(application.getBloodDonor().getAmka())) {
            throw new CustomException("An application already exists for this AMKA: " + application.getBloodDonor().getAmka());
        }

        // Ensure applicant is only a citizen
        User user = userRepository.findByEmail(application.getBloodDonor().getEmail())
                .orElseThrow(() -> new NotFoundException("User not found with email: " + application.getBloodDonor().getEmail()));

        boolean isCitizen = user.getRoles().stream()
                .allMatch(role -> role.getName().equals(Roles.ROLE_CITIZEN.name()));

        if (!isCitizen) {
            throw new CustomException("Only citizens can apply to become blood donors.");
        }

        // Calculate age from birthdate
        LocalDate birthdate = application.getBloodDonor().getBirthdate();
        LocalDate today = LocalDate.now();
        long age = ChronoUnit.YEARS.between(birthdate, today);

        if (age > 75) {
            throw new CustomException("Applicants over the age of 75 are not eligible to apply.");
        }

        return applicationRepository.save(application);
    }


    @Transactional
    public Application updateApplicationStatus(Long applicationId, Status newStatus) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("Application not found with id: " + applicationId));

        application.setStatus(newStatus);

        if (newStatus == Status.APPROVED) {
            userService.addUserRole(application.getBloodDonor().getUserId());
        }

        return applicationRepository.save(application);
    }


    @PreAuthorize("hasAuthority('SECRETARIAT')")
    public List<Application> findAwaitingApplications() {
        return applicationRepository.findByStatus(Status.AWAITING);
    }


    // Scalability
    @Transactional
    public void deleteApplication(Long applicationId) {
        applicationRepository.deleteById(applicationId);
    }


    // Scalability
    public Application findApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundException("Application not found with id: " + applicationId));
    }


    // Scalability
    public List<Application> findAllApplications() {
        return applicationRepository.findAll();
    }


    // Scalability
    public List<Application> findAllApplicationsByStatus(Status status) {
        return applicationRepository.findByStatus(status);
    }

}
