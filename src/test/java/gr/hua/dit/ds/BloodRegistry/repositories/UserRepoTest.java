package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void adminCreatesUserAccount() {
        // Simulating admin creating a user account
        User adminCreatedUser = new User();
        adminCreatedUser.setUsername("donor");
        adminCreatedUser.setPassword("password444");
        adminCreatedUser.setEmail("donor@g.com");

        User savedUser = userRepository.save(adminCreatedUser);
        assertNotNull(savedUser.getUserId());
        assertEquals("donor", savedUser.getUsername());

        // Verifying that the created user account can be retrieved
        User foundUser = userRepository.findByUserId(savedUser.getUserId());
        assertNotNull(foundUser);
        assertEquals("donor", foundUser.getUsername());
    }


    @Test
    void findByEmail() {
        // Creating a user by admin
        User adminCreatedUser = new User();
        adminCreatedUser.setUsername("thisuser");
        adminCreatedUser.setPassword("password222");
        adminCreatedUser.setEmail("thisuser@r.com");

        entityManager.persistAndFlush(adminCreatedUser);

        // Verifying that the user can be found by email
        User foundUser = userRepository.findByEmail("thisuser@r.com");
        assertNotNull(foundUser);
        assertEquals("thisuser", foundUser.getUsername());
    }


    @Test
    void existsByEmail() {
        // Creating a user by admin
        User adminCreatedUser = new User();
        adminCreatedUser.setUsername("user");
        adminCreatedUser.setPassword("password");
        adminCreatedUser.setEmail("user@g.com");

        entityManager.persistAndFlush(adminCreatedUser);

        // Checking if the user exists by email
        boolean exists = userRepository.existsByEmail("user@g.com");
        assertTrue(exists);
    }


    @Test
    void deleteUser_ByAdmin() {
        // Creating user by admin
        User userToDelete = new User();
        userToDelete.setUsername("gsadg");
        userToDelete.setPassword("4tr4ttttttttt");
        userToDelete.setEmail("fsda@vds.fds");

        User savedUser = entityManager.persistFlushFind(userToDelete);
        assertNotNull(savedUser);

        // Admin deleting user
        userRepository.deleteById(savedUser.getUserId());
        User deletedUser = userRepository.findById(savedUser.getUserId()).orElse(null);

        assertNull(deletedUser);
    }


    @Test
    public void testFindByUsername() {
        // Create and persist new User
        User newUser = new User();
        newUser.setUsername("user");
        newUser.setPassword("testpass");
        newUser.setEmail("user@j.com");
        entityManager.persistAndFlush(newUser);

        // Retrieve user
        User foundUser = userRepository.findByUsername("user").orElse(null);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("user");
    }


    @Test
    public void testUpdateUserDetails() {
        // Create and persist new User
        User user = new User();
        user.setUsername("oldusername");
        user.setPassword("password");
        user.setEmail("oldemail@example.com");
        entityManager.persistAndFlush(user);

        // Retrieve and update user
        User retrievedUser = userRepository.findById(user.getUserId()).orElse(null);
        assertNotNull(retrievedUser);
        retrievedUser.setUsername("newusername");
        retrievedUser.setEmail("emailnew@example.com");
        userRepository.save(retrievedUser);

        // Retrieve user again to verify updates
        User updatedUser = userRepository.findById(user.getUserId()).orElse(null);
        assertNotNull(updatedUser);
        assertEquals("newusername", updatedUser.getUsername());
        assertEquals("emailnew@example.com", updatedUser.getEmail());
    }
}
