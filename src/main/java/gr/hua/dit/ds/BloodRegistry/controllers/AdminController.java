package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Roles;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * Creates a new user account. This could be a secreteriat or a blood donor, based on the role specified.
     */
    @PostMapping("/create-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody User user, @RequestParam Roles role) {
        userService.createUser(user, role);
        return ResponseEntity.ok("User created successfully");
    }


    /**
     * Deletes a user account.
     */
    @DeleteMapping("/delete-user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }


    /**
     * Updates a user account.
     */
    @PutMapping("/update-user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setUserId(id);
        userService.updateUser(user, true); // Passing 'true' because the operation is performed by an admin
        return ResponseEntity.ok("User updated successfully");
    }



}
