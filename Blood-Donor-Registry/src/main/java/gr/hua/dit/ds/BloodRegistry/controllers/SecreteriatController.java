package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.entities.model.Secretariat;
import gr.hua.dit.ds.BloodRegistry.services.SecreteriatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/secreteriats")
public class SecreteriatController {

    @Autowired
    private SecreteriatService secreteriatService;

    @PostMapping
    public ResponseEntity<Secretariat> createSecreteriat(@RequestBody Secretariat secretariat) {
        return ResponseEntity.ok(secreteriatService.createSecreteriat(secretariat));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Secretariat> updateSecreteriat(@PathVariable Long id, @RequestBody Secretariat secretariat) {
        secretariat.setUserId(id);
        return ResponseEntity.ok(secreteriatService.updateSecreteriat(secretariat));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Secretariat> getSecreteriat(@PathVariable Long id) {
        return secreteriatService.findSecreteriatById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<Secretariat>> getAllSecreteriats() {
        return ResponseEntity.ok(secreteriatService.findAllSecreteriats());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSecreteriat(@PathVariable Long id) {
        secreteriatService.deleteSecreteriat(id);
        return ResponseEntity.ok().build();
    }
}
