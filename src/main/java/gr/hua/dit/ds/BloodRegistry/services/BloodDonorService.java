package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Roles;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Sex;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.BloodDonorRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.RoleRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PreAuthorize("hasAuthority('ROLE_BLOOD_DONOR')")
    @Transactional
    public BloodDonor updateBloodDonor(Long donorId, String newEmail, String newRegion, String newPhone) {
        BloodDonor existingDonor = bloodDonorRepository.findById(donorId)
                .orElseThrow(() -> new NotFoundException("Blood Donor not found with id: " + donorId));

        // Check if the new email is already used by another user
        boolean emailExists = userRepository.existsByEmail(newEmail);
        if (emailExists && !existingDonor.getEmail().equals(newEmail)) {
            throw new IllegalArgumentException("Email already in use");
        }

        existingDonor.setEmail(newEmail);
        existingDonor.setRegion(newRegion);
        existingDonor.setPhone(newPhone);

        return bloodDonorRepository.save(existingDonor);
    }


    public BloodDonor findBloodDonorByAmka(Long amka) {
        return bloodDonorRepository.findByAmka(amka)
                .orElseThrow(() -> new NotFoundException("Blood Donor not found with AMKA: " + amka));
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public void deleteBloodDonor(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));

        Role bloodDonorRole = roleRepository.findByName(Roles.ROLE_BLOOD_DONOR.name())
                .orElseThrow(() -> new NotFoundException("Role ROLE_BLOOD_DONOR not found"));

        user.getRoles().remove(bloodDonorRole);
        userRepository.save(user);
    }


    public List<BloodDonor> findEligibleDonorsForNotification() {
        List<BloodDonor> allDonors = bloodDonorRepository.findAll();
        LocalDate today = LocalDate.now();
        return allDonors.stream()
                .filter(donor -> isEligibleForDonation(donor, today))
                .collect(Collectors.toList());
    }


    boolean isEligibleForDonation(BloodDonor donor, LocalDate referenceDate) {
        if (donor.getLastDonationDate() == null) {
            return true; // Never donated
        }

        long weeksSinceLastDonation = ChronoUnit.WEEKS.between(donor.getLastDonationDate(), referenceDate);

        boolean isEligible;
        if (donor.getSex() == Sex.MALE) {
            isEligible = weeksSinceLastDonation > 12;
        } else if (donor.getSex() == Sex.FEMALE) {
            isEligible = weeksSinceLastDonation > 16;
        } else {
            isEligible = false;
        }

        return isEligible;
    }


    public Optional<BloodDonor> findBloodDonorById(Long id) {

        return bloodDonorRepository.findById(id);
    }


    public List<BloodDonor> findAllBloodDonors() {

        return bloodDonorRepository.findAll();
    }

}
