package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.repositories.BloodDonorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class BloodDonorService {

    @Autowired
    private BloodDonorRepository bloodDonorRepository;

    public BloodDonor saveBloodDonor(BloodDonor donor) {

        return bloodDonorRepository.save(donor);
    }

    @Transactional
    public BloodDonor createBloodDonor(BloodDonor bloodDonor) {

        return bloodDonorRepository.save(bloodDonor);
    }

    @Transactional
    public BloodDonor updateBloodDonor(BloodDonor bloodDonor) {

        return bloodDonorRepository.save(bloodDonor);
    }

    public Optional<BloodDonor> findBloodDonorById(Long id) {

        return bloodDonorRepository.findById(id);
    }

    public List<BloodDonor> findAllBloodDonors() {

        return bloodDonorRepository.findAll();
    }

    @Transactional
    public void deleteBloodDonor(Long id) {

        bloodDonorRepository.deleteById(id);
    }
}
