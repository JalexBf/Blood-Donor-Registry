package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Roles;
import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.RoleRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

<<<<<<< HEAD
    @Transactional
    public User createUser(User user) {
        if (!isValidUser(user)) {
            throw new IllegalArgumentException("Invalid user data");
        }
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long userId, User updatedUser) {
        return userRepository.findById(userId).map(existingUser -> {
            if (updatedUser.getUsername() != null) {
                existingUser.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getEmail() != null && EmailValidator.getInstance().isValid(updatedUser.getEmail())) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPassword() != null) {
                existingUser.setPassword(updatedUser.getPassword());
            }
            if (updatedUser.getRole() != null) {
                existingUser.setRole(updatedUser.getRole());
            }
            // You can add more fields to update as needed
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
    }
=======
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public User createUser(User user, Roles role) {
        Optional<Role> roleOptional = roleRepository.findByName(role.name());
        if (!roleOptional.isPresent()) {
            throw new NotFoundException("Role not found: " + role);
        }
        user.setRole(roleOptional.get());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public User updateUser(User user, boolean isAdmin) {
        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + user.getUserId()));

        if (isAdmin && user.getRole() != null) {
            existingUser.setRole(user.getRole()); // Allow role change if admin
        }

        existingUser.setEmail(user.getEmail());
        existingUser.setUsername(user.getUsername());

        return userRepository.save(existingUser);
    }

>>>>>>> backup-2b0394c

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

<<<<<<< HEAD
    public List<User> findAllUsers() {
        return userRepository.findAll();
=======

    public List<User> findAllDonors() {
        Role donorRole = roleRepository.findByName(Roles.ROLE_BLOOD_DONOR.name()).orElseThrow();
        return userRepository.findAllByRole(donorRole);
>>>>>>> backup-2b0394c
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

<<<<<<< HEAD
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
=======

    public boolean existsByEmail(String username) {
        return userRepository.existsByEmail(username);
    }


    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword)); // Encrypt new password
        user.setPasswordLastChangedDate(LocalDate.now());
        userRepository.save(user);
    }


    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }
}
>>>>>>> backup-2b0394c

    private boolean isValidUser(User user) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        return user.getUsername() != null && !user.getUsername().trim().isEmpty()
                && user.getEmail() != null && emailValidator.isValid(user.getEmail())
                && user.getPassword() != null && !user.getPassword().trim().isEmpty()
                && user.getRole() != null && !user.getRole().trim().isEmpty();
    }
}