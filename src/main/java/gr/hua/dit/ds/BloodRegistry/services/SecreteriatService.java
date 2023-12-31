package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.model.Secreteriat;
import gr.hua.dit.ds.BloodRegistry.repositories.SecreteriatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SecreteriatService {

    @Autowired
    private SecreteriatRepository secreteriatRepository;


    @Transactional
    public Secreteriat createSecreteriat(Secreteriat secreteriat) {
        return secreteriatRepository.save(secreteriat);
    }

    @Transactional
    public Secreteriat updateSecreteriat(Secreteriat secreteriat) {
        return secreteriatRepository.save(secreteriat);
    }

    public Optional<Secreteriat> findSecreteriatById(Long id) {
        return secreteriatRepository.findById(id);
    }

    public List<Secreteriat> findAllSecreteriats() {
        return secreteriatRepository.findAll();
    }

    @Transactional
    public void deleteSecreteriat(Long id) {
        secreteriatRepository.deleteById(id);
    }
}
