package gr.hua.dit.ds.BloodRegistry.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.services.BloodDonorService;

import java.util.List;


@RestController
@RequestMapping("/blood-donors")
public class BloodDonorController {

    @Autowired
    private BloodDonorService bloodDonorService;


    @PutMapping("/update/{donorId}")
    @PreAuthorize("hasAuthority('ROLE_SECRETARIAT') or hasAuthority('ROLE_BLOOD_DONOR')")
    public ResponseEntity<BloodDonor> updateBloodDonor(@PathVariable Long donorId, @RequestBody BloodDonor donorDetails) {
        BloodDonor updatedDonor = bloodDonorService.updateBloodDonor(donorId, donorDetails.getEmail(), donorDetails.getRegion(), donorDetails.getPhone());
        return new ResponseEntity<>(updatedDonor, HttpStatus.OK);
    }


    @GetMapping("/searchByAmka")
    @PreAuthorize("hasAuthority('ROLE_SECRETARIAT')")
    public ResponseEntity<BloodDonor> getBloodDonorByAmka(@RequestParam Long amka) {
        BloodDonor donor = bloodDonorService.findBloodDonorByAmka(amka);
        return new ResponseEntity<>(donor, HttpStatus.OK);
    }


    @GetMapping("/eligibleForDonation")
    @PreAuthorize("hasAuthority('ROLE_SECRETARIAT')")
    public ResponseEntity<List<BloodDonor>> getEligibleDonors() {
        List<BloodDonor> eligibleDonors = bloodDonorService.findEligibleDonorsForNotification();
        return new ResponseEntity<>(eligibleDonors, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<Iterable<BloodDonor>> getAllBloodDonors() {
        return ResponseEntity.ok(bloodDonorService.findAllBloodDonors());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBloodDonor(@PathVariable Long id) {
        bloodDonorService.deleteBloodDonor(id);
        return ResponseEntity.ok().build();
    }
}

