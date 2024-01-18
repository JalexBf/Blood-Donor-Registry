package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class UserRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserId_WhenUserExists() {
        User newUser = new User();
        newUser.setUsername("test");
        newUser.setPassword("123123123123");
        newUser.setEmail("user@user.com");

        User savedUser = entityManager.persistFlushFind(newUser);
        User foundUser = userRepository.findByUserId(savedUser.getUserId());

        assertNotNull(foundUser);
        assertEquals("test", foundUser.getUsername());
    }

    @Test
    void findByEmail_WhenEmailExists() {
        User newUser = new User();
        newUser.setUsername("another");
        newUser.setPassword("password456");
        newUser.setEmail("another@a.com");

        entityManager.persistAndFlush(newUser);
        User foundUser = userRepository.findByEmail("another@a.com");

        assertNotNull(foundUser);
        assertEquals("another", foundUser.getUsername());
    }

    @Test
    void existsByEmail_WhenEmailExists() {
        User newUser = new User();
        newUser.setUsername("rds");
        newUser.setPassword("789789789789");
        newUser.setEmail("email@e.com");

        entityManager.persistAndFlush(newUser);
        boolean exists = userRepository.existsByEmail("email@e.com");

        assertTrue(exists);
    }

    @Test
    void saveUser_AndFindById() {
        User user = new User();
        user.setUsername("saveUser");
        user.setPassword("savePass");
        user.setEmail("save@example.com");

        User savedUser = userRepository.save(user);
        assertNotNull(savedUser);
        assertNotNull(savedUser.getUserId());

        User foundUser = userRepository.findById(savedUser.getUserId()).orElse(null);
        assertNotNull(foundUser);
        assertEquals("saveUser", foundUser.getUsername());
    }

    @Test
    void deleteUser_AndCheckNotExist() {
        User user = new User();
        user.setUsername("deleteUser");
        user.setPassword("deletePass");
        user.setEmail("delete@example.com");

        User savedUser = entityManager.persistFlushFind(user);
        assertNotNull(savedUser);

        userRepository.deleteById(savedUser.getUserId());
        User deletedUser = userRepository.findById(savedUser.getUserId()).orElse(null);

        assertNull(deletedUser);
    }
}
