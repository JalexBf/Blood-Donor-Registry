package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Sex;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.repositories.BloodDonorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
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

    public boolean isEligibleForDonation(BloodDonor donor) {

        LocalDate today = LocalDate.now();
        Period periodSinceLastDonation = Period.between(donor.getLastDonationDate(), today);

        if (donor.getSex() == Sex.MALE && periodSinceLastDonation.toTotalMonths() < 3) {
            return false; // Males can donate every 12 weeks
        } else if (donor.getSex() == Sex.FEMALE && periodSinceLastDonation.toTotalMonths() < 4) {
            return false; // Females can donate every 16 weeks
        }
        if (donor.getBirthdate() != null) {
            int age = Period.between(donor.getBirthdate(), LocalDate.now()).getYears();
            if (age > 65) {
                return false; // Not eligible if outside the age range
            }
        }

        return true;
    }
}
