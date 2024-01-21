package gr.hua.dit.ds.BloodRegistry.services;


import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Donation;
import gr.hua.dit.ds.BloodRegistry.exceptions.InvalidRequestException;
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
    public Donation createDonation(Donation donation) {

        Status donorStatus = donation.getBloodDonor().getRegistration().getStatus();

        if (donation.getBloodDonor().getRegistration().getStatus() != Status.APPROVED) {
            throw new InvalidRequestException("Donor is not approved for donation.");
        }
        BloodDonor donor = donation.getBloodDonor();
        donor.setLastDonationDate(LocalDate.now()); // Set the current date as the last donation date
        bloodDonorRepository.save(donor);

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
