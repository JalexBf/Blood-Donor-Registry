package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<Registration> createRegistration(@RequestBody Registration registration) {
        return ResponseEntity.ok(registrationService.createRegistration(registration));
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

    @GetMapping
    public ResponseEntity<Iterable<Registration>> getAllRegistrations() {
        return ResponseEntity.ok(registrationService.findAllRegistrations());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
        return ResponseEntity.ok().build();
    }
}
