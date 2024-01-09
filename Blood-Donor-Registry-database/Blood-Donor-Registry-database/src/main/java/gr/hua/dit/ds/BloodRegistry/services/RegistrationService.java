package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Transactional
    public Registration createRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    @Transactional
    public Registration updateRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    public Registration findRegistrationById(Long registrationId) {
        return registrationRepository.findById(registrationId)
                .orElseThrow(() -> new NotFoundException("Registration not found with id: " + registrationId));
    }

    public List<Registration> findAllRegistrations() {
        return registrationRepository.findAll();
    }

    @Transactional
    public void deleteRegistration(Long registrationId) {
        registrationRepository.deleteById(registrationId);
    }
}
