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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    void updateUser_Success() {
        Long userId = 1L;
        String newEmail = "newemail@new.com";
        String newUsername = "new";

        User existingUser = new User();
        existingUser.setUserId(userId);
        existingUser.setEmail("oldemail@old.com");
        existingUser.setUsername("old");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = userService.updateUser(userId, newEmail, newUsername);

        assertNotNull(updatedUser);
        assertEquals(newEmail, updatedUser.getEmail());
        assertEquals(newUsername, updatedUser.getUsername());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }


    @Test
    void updateUserContactInfo_UserNotFound() {
        Long nonExistentUserId = 99L;
        when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () ->
                userService.updateUser(nonExistentUserId, "newemail@new.com", "new")
        );

        String expectedMessage = "User not found with id: " + nonExistentUserId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
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
        when(passwordEncoder.encode("newpassword")).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.changePassword(user.getUserId(), "newpassword");

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
    void updateUserRole_Success() {
        String newRoleName = Roles.ROLE_BLOOD_DONOR.name();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(roleRepository.findByName(newRoleName)).thenReturn(Optional.of(new Role(2L, newRoleName, Set.of())));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.updateUserRole(user.getUsername(), newRoleName);

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(roleRepository, times(1)).findByName(newRoleName);
        verify(userRepository, times(1)).save(user);
        assertEquals(newRoleName, user.getRole().getName());
    }



    @Test
    void updateUserRole_UserNotFound() {
        String username = "nonexistentuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            userService.updateUserRole(username, Roles.ROLE_BLOOD_DONOR.name());
        });

        String expectedMessage = "User Not Found with username: " + username;
        assertEquals(expectedMessage, exception.getMessage());
    }


    @Test
    void updateUserRole_RoleNotFound() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(roleRepository.findByName("NON_EXISTENT_ROLE")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUserRole(user.getUsername(), "NON_EXISTENT_ROLE");
        });

        String expectedMessage = "Role not found with name: NON_EXISTENT_ROLE";
        assertEquals(expectedMessage, exception.getMessage());
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
