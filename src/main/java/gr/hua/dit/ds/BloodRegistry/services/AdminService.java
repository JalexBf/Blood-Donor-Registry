package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.DTO.UserDTO;
import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;


@Service
public class AdminService {

    @Autowired
    private RoleService roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public User createUser(User newUser, String roleName) {
        // Find the role in the database
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Error: Role " + roleName + " is not found."));

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        // Assign role to the user
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        newUser.setRoles(roles);

        return userRepository.save(newUser);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public User updateUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        user.setAccountNonExpired(userDTO.isAccountNonExpired());
        user.setAccountNonLocked(userDTO.isAccountNonLocked());
        user.setCredentialsNonExpired(userDTO.isCredentialsNonExpired());
        user.setEnabled(userDTO.isEnabled());
        user.setPasswordLastChangedDate(userDTO.getPasswordLastChangedDate());
        user.setFailedLoginAttempts(userDTO.getFailedLoginAttempts());
        user.setAccountLockDate(userDTO.getAccountLockDate());

        Set<Role> roles = new HashSet<>();
        for (String roleName : userDTO.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new IllegalArgumentException("Role not found with name: " + roleName));
            roles.add(role);
        }
        user.setRoles(roles);

        return userRepository.save(user);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

}
