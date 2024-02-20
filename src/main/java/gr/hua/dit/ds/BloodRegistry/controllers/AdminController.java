package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Roles;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.services.BloodDonorService;
import gr.hua.dit.ds.BloodRegistry.services.SecretariatService;
import gr.hua.dit.ds.BloodRegistry.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    private BloodDonorService bloodDonorService;

    private SecretariatService secretariatService;


    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User newUser, @RequestParam String roleName) {
        User user = userService.createUser(newUser, roleName);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @DeleteMapping("/deleteDonor/{id}")
    public ResponseEntity<?> deleteBloodDonor(@PathVariable Long id) {
        bloodDonorService.deleteBloodDonor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/deleteSecretariat/{id}")
    public ResponseEntity<?> deleteSecretariat(@PathVariable Long id) {
        secretariatService.deleteSecretariat(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/updateDonor/{donorId}")
    public ResponseEntity<BloodDonor> updateBloodDonor(@PathVariable Long donorId,
                                                       @RequestBody BloodDonor donorDetails) {
        BloodDonor updatedDonor = bloodDonorService.updateBloodDonor(donorId,
                donorDetails.getEmail(),
                donorDetails.getRegion(),
                donorDetails.getPhone());
        return new ResponseEntity<>(updatedDonor, HttpStatus.OK);
    }
}
