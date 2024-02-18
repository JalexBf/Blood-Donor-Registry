package gr.hua.dit.ds.BloodRegistry.controllers;

<<<<<<< HEAD
=======
import gr.hua.dit.ds.BloodRegistry.entities.enums.Roles;
>>>>>>> backup-2b0394c
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;

=======
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


>>>>>>> backup-2b0394c
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

<<<<<<< HEAD
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    // Additional admin endpoints can be added here
=======
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
    @PutMapping("/update-user/{id}/contact-info")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserContactInfo(@PathVariable Long id,
                                                   @RequestParam String email,
                                                   @RequestParam String username) {
        userService.updateUser(id, email, username);
        return ResponseEntity.ok("User contact information updated successfully");
    }


    @PutMapping("/update-user/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestBody String roleName) {
        User user = userService.findUserById(id); // Find the user by ID
        userService.updateUserRole(user.getUsername(), roleName); // Update the user's role
        return ResponseEntity.ok("User role updated successfully");
    }


>>>>>>> backup-2b0394c
}
