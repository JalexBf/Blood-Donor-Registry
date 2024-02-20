package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.entities.model.Donation;
import gr.hua.dit.ds.BloodRegistry.services.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;


    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ROLE_SECRETARIAT')")
    public ResponseEntity<Donation> registerDonation(@RequestParam Long amka, @RequestBody Donation donation) {
        Donation registeredDonation = donationService.registerDonation(amka, donation);
        return new ResponseEntity<>(registeredDonation, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonation(@PathVariable Long id) {
        return donationService.findDonationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<Donation>> getAllDonations() {
        return ResponseEntity.ok(donationService.findAllDonations());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Long id) {
        donationService.deleteDonation(id);
        return ResponseEntity.ok().build();
    }
}
