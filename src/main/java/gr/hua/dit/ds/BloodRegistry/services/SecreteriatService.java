package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.model.Secreteriat;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.SecreteriatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class SecreteriatService {

    @Autowired
    private SecreteriatRepository secreteriatRepository;


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public Secreteriat createSecreteriat(Secreteriat secreteriat) {
        // Add validation logic here
        return secreteriatRepository.save(secreteriat);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public Secreteriat updateSecreteriat(Secreteriat secreteriat) {
        secreteriatRepository.findById(secreteriat.getUserId())
                .orElseThrow(() -> new NotFoundException("Secreteriat not found with id: " + secreteriat.getUserId()));
        // Add validation logic here
        return secreteriatRepository.save(secreteriat);
    }

    public Optional<Secreteriat> findSecreteriatById(Long id) {
        return secreteriatRepository.findByUserId(id);
    }


    public List<Secreteriat> findAllSecreteriats() {
        return secreteriatRepository.findAll();
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public void deleteSecreteriat(Long id) {
        secreteriatRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Secreteriat not found with id: " + id));
        secreteriatRepository.deleteById(id);
    }

}
