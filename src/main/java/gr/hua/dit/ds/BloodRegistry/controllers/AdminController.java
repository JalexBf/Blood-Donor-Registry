package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.DTO.UserDTO;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Roles;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.services.AdminService;
import gr.hua.dit.ds.BloodRegistry.services.BloodDonorService;
import gr.hua.dit.ds.BloodRegistry.services.SecretariatService;
import gr.hua.dit.ds.BloodRegistry.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user); // HTTP 200 OK with user data
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/users/role/{roleName}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String roleName) {
        List<User> users = userService.findUsersByRoleName(roleName);
        return ResponseEntity.ok(users); // HTTP 200 OK with list of users
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User newUser = adminService.createUser(userDTO.toUser(), userDTO.getRoles().iterator().next());
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        User updatedUser = adminService.updateUser(userId, userDTO);
        return ResponseEntity.ok(updatedUser);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }


}
