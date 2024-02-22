package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Roles;
import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.RoleRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Transactional
    public void addUserRole(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        Role bloodDonorRole = roleRepository.findByName(Roles.ROLE_BLOOD_DONOR.name())
                .orElseThrow(() -> new NotFoundException("Role ROLE_BLOOD_DONOR not found"));

        // Check the user already has the blood donor role
        boolean hasBloodDonorRole = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(Roles.ROLE_BLOOD_DONOR.name()));

        // Only add the blood donor role if the user doesn't already have it
        if (!hasBloodDonorRole) {
            user.getRoles().add(bloodDonorRole);
            userRepository.save(user);
        }
    }


    @PreAuthorize("hasAuthority('ROLE_CITIZEN')")
    @Transactional
    public void changeUserPassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        // Verify old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid old password.");
        }

        // Update to the new password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> findUsersByRoleName(String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with name: " + roleName));
        return userRepository.findAllByRoles(role);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }
}

