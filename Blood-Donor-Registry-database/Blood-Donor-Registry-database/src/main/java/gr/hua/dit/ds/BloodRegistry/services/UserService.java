package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.RoleRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import gr.hua.dit.ds.BloodRegistry.config.AppConfig;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public User createUserWithRoles(User user, Set<Long> roleIds) {
        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            roleRepository.findById(roleId).ifPresent(roles::add);
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Transactional
    public User createUser(User user) {

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User user) {

        return userRepository.save(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public List<User> findAllUsers() {

        return userRepository.findAll();
    }

    @Transactional
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    @Transactional
    public Integer saveUser(User user) {
        String passwd= user.getPassword();
        String encodedPasswod = passwordEncoder.encode(passwd);
        user.setPassword(encodedPasswod);

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        user = userRepository.save(user);
        return user.getId();
    }


    public boolean existsByEmail(String username) {
        return userRepository.existsByEmail(username);
    }
}

