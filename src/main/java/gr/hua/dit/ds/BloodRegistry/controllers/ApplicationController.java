package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.Application;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.ApplicationRepository;
import gr.hua.dit.ds.BloodRegistry.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationRepository applicationRepository;


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_CITIZEN')")
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        Application createdApplication = applicationService.createApplication(application);
        return new ResponseEntity<>(createdApplication, HttpStatus.CREATED);
    }


    @PutMapping("/updateStatus/{applicationId}")
    @PreAuthorize("hasAuthority('ROLE_SECRETARIAT') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Application> updateApplicationStatus(@PathVariable Long applicationId, @RequestParam Status newStatus) {
        Application updatedApplication = applicationService.updateApplicationStatus(applicationId, newStatus);
        return new ResponseEntity<>(updatedApplication, HttpStatus.OK);
    }


    @GetMapping("/{applicationId}")
    public ResponseEntity<Application> findApplicationById(@PathVariable Long applicationId) {
        Application application = applicationService.findApplicationById(applicationId);
        return new ResponseEntity<>(application, HttpStatus.OK);
    }


    @GetMapping("/status/{status}")
    @PreAuthorize("hasAuthority('ROLE_SECRETARIAT') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Application>> findAllApplicationsByStatus(@PathVariable Status status) {
        List<Application> applications = applicationService.findAllApplicationsByStatus(status);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }
}
