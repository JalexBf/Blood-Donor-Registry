package gr.hua.dit.ds.BloodRegistry.entities;

import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserTest {

    private User user;
    private Validator validator;

    @BeforeEach
    void setUp() {
        // Initialize User with test data
        user = new User();
        user.setUserId(1L);
        user.setUsername("test");
        user.setPassword("testpassword");
        user.setEmail("test@test.com");
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        // Validator setup
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, user.getUserId());
        assertEquals("test", user.getUsername());
        assertEquals("testpassword", user.getPassword());
        assertEquals("test@test.com", user.getEmail());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }

    @Test
    void testUserWithInvalidEmail() {
        user.setEmail("invalid");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "email");
        assertFalse(violations.isEmpty(), "Invalid email should result validation error");
    }

    @Test
    void testUserWithEmptyUsername() {
        user.setUsername("");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "username");
        assertFalse(violations.isEmpty(), "Empty username should result validation error");
    }

    @Test
    void testUserWithNullPassword() {
        user.setPassword(null);
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "password");
        assertFalse(violations.isEmpty(), "Null password should result validation error");
    }

    @Test
    void testValidUser() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "No violations should be present for valid user");
    }
}