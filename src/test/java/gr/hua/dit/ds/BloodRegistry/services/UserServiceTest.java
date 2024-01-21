package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Roles;
import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.RoleRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@k.com");

        role = new Role();
        role.setRoleId(1L);
        role.setName(Roles.ROLE_ADMIN.name());
    }


    @Test
    void createUser() {
        when(roleRepository.findByName(Roles.ROLE_ADMIN.name())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user, Roles.ROLE_ADMIN);

        assertNotNull(createdUser);
        verify(userRepository).save(user);
        assertEquals("encodedPassword", createdUser.getPassword());
    }


    @Test
    void updateUser() {
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(user, true);

        assertNotNull(updatedUser);
        verify(userRepository).save(user);
        assertEquals("testuser", updatedUser.getUsername());
    }


    @Test
    void findUserById() {
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));

        User foundUser = userService.findUserById(user.getUserId());

        assertNotNull(foundUser);
        assertEquals(user.getUserId(), foundUser.getUserId());
    }


    @Test
    void deleteUsere() {
        doNothing().when(userRepository).deleteById(user.getUserId());

        userService.deleteUser(user.getUserId());

        verify(userRepository).deleteById(user.getUserId());
    }


    @Test
    void changePassword() {
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.changePassword(user.getUserId(), "newPassword");

        verify(userRepository).save(user);
        assertEquals("encodedNewPassword", user.getPassword());
    }


    @Test
    void createUser_Success() {
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user, Roles.ROLE_BLOOD_DONOR);

        assertNotNull(createdUser);
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void createUser_RoleNotFound() {
        when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.createUser(user, Roles.ROLE_BLOOD_DONOR));
    }

    @Test
    void updateUser_Success() {
        User updatedUser = new User();
        updatedUser.setUserId(1L); // Ensure this is not null
        updatedUser.setEmail("updated@k.com");
        updatedUser.setUsername("update");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUser(updatedUser, true);

        assertNotNull(result);
        assertEquals("updated@k.com", result.getEmail());
        assertEquals("update", result.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void updateUser_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        User updatedUser = new User();
        updatedUser.setUserId(99L);

        assertThrows(NotFoundException.class, () -> userService.updateUser(updatedUser, true));
    }


    @Test
    void findUserById_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User foundUser = userService.findUserById(1L);

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }


    @Test
    void findUserById_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findUserById(99L));
    }


    @Test
    void findAllUsers() {
        User user = new User();

        when(userRepository.findAll()).thenReturn(List.of(user));

        Iterable<User> users = userService.findAllUsers();

        assertNotNull(users);
        // Since users is Iterable it is converted to a Collection to check its size
        List<User> userList = StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());
        assertFalse(userList.isEmpty());
        assertEquals(1, userList.size());
    }


    @Test
    void deleteUser_Success() {
        doNothing().when(userRepository).deleteById(anyLong());

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(anyLong());
    }


    @Test
    void existsByEmail() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        boolean exists = userService.existsByEmail("fsad@m.com");

        assertTrue(exists);
    }


    @Test
    void changePassword_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("newEncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.changePassword(1L, "newpass");

        verify(passwordEncoder, times(1)).encode("newpass");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void changePassword_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.changePassword(99L, "newpass"));
    }
}
