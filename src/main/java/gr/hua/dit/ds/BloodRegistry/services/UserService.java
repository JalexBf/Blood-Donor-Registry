package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.DTO.UserDto;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Roles;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secreteriat;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.BloodDonorRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.RoleRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.SecreteriatRepository;
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
    private BloodDonorRepository bloodDonorRepository;

    @Autowired
    private SecreteriatRepository secreteriatRepository;

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
<<<<<<< Updated upstream
    public User updateUser(UserDto userDto,Long userId) {

        Role role =roleRepository.findById(userDto.getRole()).orElseThrow(()->new RuntimeException("role not found"));
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));


       if(existingUser instanceof BloodDonor){
           BloodDonor existingBloodDonor= bloodDonorRepository.findByUserId(userId).orElseThrow(()-> new NotFoundException("Blood donor not found"));

           existingBloodDonor.setUsername(userDto.getUsername());
           existingBloodDonor.setPassword(passwordEncoder.encode(userDto.getPassword()));
           existingBloodDonor.setEmail(userDto.getEmail());
           existingBloodDonor.setFirstname(userDto.getFirstname());
           existingBloodDonor.setLastname(userDto.getLastname());
           existingBloodDonor.setBirthdate(userDto.getBirthdate());
           existingBloodDonor.setBloodType(userDto.getBloodType());
           existingBloodDonor.setAmka(userDto.getAmka());
           existingBloodDonor.setPhone(userDto.getPhone());
           existingBloodDonor.setSex(userDto.getSex());
           existingBloodDonor.setRegion(userDto.getRegion());





           return bloodDonorRepository.saveAndFlush(existingBloodDonor);
       }

        if(existingUser instanceof Secreteriat){
            Secreteriat existingSecretariat= secreteriatRepository.findByUserId(userId).orElseThrow(()-> new NotFoundException("Blood donor not found"));
            existingSecretariat.setUsername(userDto.getUsername());
            existingSecretariat.setEmail(userDto.getEmail());
            existingSecretariat.setFirstname(userDto.getFirstname());
            existingSecretariat.setLastname(userDto.getLastname());



            return secreteriatRepository.saveAndFlush(existingSecretariat);
        }


=======
    public void updateUserRole(String username, String roleName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with name: " + roleName));

        user.setRole(role);
        userRepository.save(user);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public User updateUser(Long userId, String newEmail, String newUsername) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        existingUser.setEmail(newEmail);
        existingUser.setUsername(newUsername);
>>>>>>> Stashed changes

        return userRepository.save(existingUser);
    }


    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }


    public List<User> findAllDonors() {
        Role donorRole = roleRepository.findByName(Roles.ROLE_BLOOD_DONOR.name()).orElseThrow();
        return userRepository.findAllByRole(donorRole);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }


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

