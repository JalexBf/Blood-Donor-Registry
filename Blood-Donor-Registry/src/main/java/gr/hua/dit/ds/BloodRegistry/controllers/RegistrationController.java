package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.DTO.RegistrationdDto;
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


    @GetMapping
    public ResponseEntity<Iterable<Registration>> getAllRegistrations() {
        return ResponseEntity.ok(registrationService.findAllRegistrations());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{id}/approve")
    public ResponseEntity<Registration> approveRegistration(@PathVariable Long id) {
        try {
            Registration approvedRegistration = registrationService.approveRegistration(id);
            return ResponseEntity.ok(approvedRegistration);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/{id}/reject")
    public ResponseEntity<Registration> rejectRegistration(@PathVariable Long id) {
        try {
            Registration rejectedRegistration = registrationService.rejectRegistration(id);
            return ResponseEntity.ok(rejectedRegistration);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
