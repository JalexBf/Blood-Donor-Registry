package gr.hua.dit.ds.BloodRegistry.services;


import gr.hua.dit.ds.BloodRegistry.entities.model.Donation;
import gr.hua.dit.ds.BloodRegistry.repositories.DonationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Transactional
    public Donation createDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    @Transactional
    public Donation updateDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    public Optional<Donation> findDonationById(Long id) {
        return donationRepository.findById(id);
    }

    public List<Donation> findAllDonations() {
        return donationRepository.findAll();
    }

    @Transactional
    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }
}
