package gr.hua.dit.ds.BloodRegistry.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.services.BloodDonorService;


@RestController
@RequestMapping("/blood-donors")
public class BloodDonorController {

    @Autowired
    private BloodDonorService bloodDonorService;

    @PostMapping
    public ResponseEntity<BloodDonor> createBloodDonor(@RequestBody BloodDonor bloodDonor) {
        return ResponseEntity.ok(bloodDonorService.createBloodDonor(bloodDonor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloodDonor> updateBloodDonor(@PathVariable Long id, @RequestBody BloodDonor bloodDonor) {
        bloodDonor.setUserId(id); // Assuming setUser_id is the method to set ID in BloodDonor
        return ResponseEntity.ok(bloodDonorService.updateBloodDonor(bloodDonor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodDonor> getBloodDonor(@PathVariable Long id) {
        return bloodDonorService.findBloodDonorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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

