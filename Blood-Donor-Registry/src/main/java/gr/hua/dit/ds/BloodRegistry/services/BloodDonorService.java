package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Sex;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.repositories.BloodDonorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BloodDonorService {

    @Autowired
    private BloodDonorRepository bloodDonorRepository;

    public BloodDonor saveBloodDonor(BloodDonor donor) {

        return bloodDonorRepository.save(donor);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public BloodDonor createBloodDonor(BloodDonor bloodDonor) {
        // Validation here
        return bloodDonorRepository.save(bloodDonor);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public BloodDonor updateBloodDonor(BloodDonor bloodDonor) {
        // Validation here
        return bloodDonorRepository.save(bloodDonor);
    }


    public Optional<BloodDonor> findBloodDonorById(Long id) {

        return bloodDonorRepository.findByUserId(id);
    }


    public List<BloodDonor> findAllBloodDonors() {

        return bloodDonorRepository.findAll();
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public void deleteBloodDonor(Long id) {

        bloodDonorRepository.deleteById(id);
    }


    public List<BloodDonor> findEligibleDonorsForNotification() {
        List<BloodDonor> allDonors = bloodDonorRepository.findAll();
        LocalDate today = LocalDate.now();
        return allDonors.stream()
                .filter(donor -> isEligibleForDonation(donor, today))
                .collect(Collectors.toList());
    }

    public boolean isEligibleForDonation(BloodDonor donor, LocalDate referenceDate) {
        if (donor.getLastDonationDate() == null) {
            return true; // Never donated
        }

        long weeksSinceLastDonation = ChronoUnit.WEEKS.between(donor.getLastDonationDate(), referenceDate);

        boolean isEligible;
        if (donor.getSex() == Sex.MALE) {
            isEligible = weeksSinceLastDonation > 16;
        } else if (donor.getSex() == Sex.FEMALE) {
            isEligible = weeksSinceLastDonation > 12;
        } else {
            isEligible = false;
        }

        return isEligible;
    }





}
