package gr.hua.dit.ds.BloodRegistry.services;


import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Donation;
import gr.hua.dit.ds.BloodRegistry.exceptions.InvalidRequestException;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.BloodDonorRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.DonationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private BloodDonorRepository bloodDonorRepository;

    
    @Transactional
    public Donation registerDonation(Long amka, Donation donationDetails) {
        BloodDonor donor = bloodDonorRepository.findByAmka(amka)
                .orElseThrow(() -> new NotFoundException("Blood Donor not found with AMKA: " + amka));

        donationDetails.setBloodDonor(donor);
        donationDetails.setDonationDate(LocalDate.now()); // Use the current date for the donation date
        return donationRepository.save(donationDetails);
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
