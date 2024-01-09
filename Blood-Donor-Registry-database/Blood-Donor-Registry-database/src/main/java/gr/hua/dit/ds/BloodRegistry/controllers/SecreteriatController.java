package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.entities.model.Secreteriat;
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
    public ResponseEntity<Secreteriat> createSecreteriat(@RequestBody Secreteriat secreteriat) {
        return ResponseEntity.ok(secreteriatService.createSecreteriat(secreteriat));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Secreteriat> updateSecreteriat(@PathVariable Long id, @RequestBody Secreteriat secreteriat) {
        secreteriat.setUserId(id);
        return ResponseEntity.ok(secreteriatService.updateSecreteriat(secreteriat));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Secreteriat> getSecreteriat(@PathVariable Long id) {
        return secreteriatService.findSecreteriatById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<Secreteriat>> getAllSecreteriats() {
        return ResponseEntity.ok(secreteriatService.findAllSecreteriats());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSecreteriat(@PathVariable Long id) {
        secreteriatService.deleteSecreteriat(id);
        return ResponseEntity.ok().build();
    }
}
