package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.Application;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secretariat;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.security.UserDetailsImpl;
import gr.hua.dit.ds.BloodRegistry.services.ApplicationService;
import gr.hua.dit.ds.BloodRegistry.services.SecretariatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;


@RestController
@RequestMapping("/secretariats")
public class SecretariatController {

    @Autowired
    private SecretariatService secretariatService;




    @GetMapping("/pendingApplications")
    @PreAuthorize("hasAuthority('ROLE_SECRETARIAT')")
    public ResponseEntity<List<Application>> getPendingApplications() {
        List<Application> pendingApplications = secretariatService.getPendingApplications();
        return new ResponseEntity<>(pendingApplications, HttpStatus.OK);
    }


    @PostMapping("/applications/{applicationId}/decision")
    @PreAuthorize("hasAuthority('ROLE_SECRETARIAT')")
    public ResponseEntity<?> updateApplicationDecision(@PathVariable Long applicationId, @RequestParam Status status, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long secretariatId = userDetails.getId(); // Retrieve the ID of the currently logged-in secretariat
        secretariatService.updateApplicationDecision(applicationId, status, secretariatId);
        return ResponseEntity.ok("Application status updated successfully.");
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteSecretariat(@PathVariable Long id) {
        secretariatService.deleteSecretariat(id);
    }


    @GetMapping("/findById/{id}")
    @PreAuthorize("hasAuthority('ROLE_SECRETARIAT') or hasAuthority('ROLE_ADMIN')")
    public Secretariat findsecretariatById(@PathVariable Long id) {
        return secretariatService.findSecretariatById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Secretariat not found"));
    }


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Secretariat> findAllsecretariats() {
        return secretariatService.findAllSecretariats();
    }
}
