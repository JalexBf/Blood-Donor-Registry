package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.DTO.RegistrationdDto;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.services.BloodDonorService;
import gr.hua.dit.ds.BloodRegistry.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private BloodDonorService bloodDonorService;

    @PostMapping
    public ResponseEntity<Registration> createRegistration(@RequestBody RegistrationdDto registrationdDto) {
        return ResponseEntity.ok(registrationService.createRegistration(registrationdDto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Registration> getRegistration(@PathVariable Long id) {
        try {
            Registration registration = registrationService.findRegistrationById(id);
            return ResponseEntity.ok(registration);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bloodDonor/{id}")
    public ResponseEntity<BloodDonor> getBloodDonorByRegistrationId(@PathVariable Long id) {
        BloodDonor bloodDonor = registrationService.findBloodDonorByRegistrationId(id);
        if (bloodDonor != null) {
            // BloodDonor found
            return ResponseEntity.ok(bloodDonor);
        } else {
            // BloodDonor not found
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserByRegistrationId(@PathVariable Long id) {
        Optional<User> user = Optional.ofNullable(registrationService.findUserByRegistrationId(id));
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<Registration>> getAllRegistrations() {
        return ResponseEntity.ok(registrationService.findAllRegistrations());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveRegistration(@PathVariable Long id) {
        try {
            BloodDonor bloodDonor = registrationService.findBloodDonorByRegistrationId(id);
            User user = registrationService.findUserByRegistrationId(id);

            if (bloodDonor != null) {
                Registration approvedRegistration = registrationService.approveRegistration(id);

                if (!bloodDonorService.isEligibleForDonation(bloodDonor, LocalDate.now())) {
                    System.out.println("The donor is not eligible for donation today.");
                    return ResponseEntity.ok(approvedRegistration);
                } else {
                    registrationService.sendEmail(user.getEmail());
                    return ResponseEntity.noContent().build();
                }
            } else {
                System.out.println("No blood donor found for the given registration ID.");
                return ResponseEntity.badRequest().body("No blood donor found for the given registration ID.");
            }
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/reject/{id}")
    public ResponseEntity<Registration> rejectRegistration(@PathVariable Long id) {
        try {
            Registration rejectedRegistration = registrationService.rejectRegistration(id);
            return ResponseEntity.ok(rejectedRegistration);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
