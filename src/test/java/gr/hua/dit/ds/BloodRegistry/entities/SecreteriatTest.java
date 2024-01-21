package gr.hua.dit.ds.BloodRegistry.entities;

import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secreteriat;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SecreteriatTest {

    private Secreteriat secreteriat;
    private Validator validator;

    @BeforeEach
    void setUp() {
        secreteriat = new Secreteriat();
        secreteriat.setUserId(1L);
        secreteriat.setUsername("user");
        secreteriat.setPassword("123456789");
        secreteriat.setEmail("max@a.com");

        secreteriat.setFirstname("cat");
        secreteriat.setLastname("cat");

        secreteriat.setAccountNonExpired(true);
        secreteriat.setAccountNonLocked(true);
        secreteriat.setCredentialsNonExpired(true);
        secreteriat.setEnabled(true);
        List<Registration> registrations = new ArrayList<>();
        secreteriat.setRegistrations(registrations);

        // Validator setup
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, secreteriat.getUserId());
        assertEquals("user", secreteriat.getUsername());
        assertEquals("123456789", secreteriat.getPassword());
        assertEquals("max@a.com", secreteriat.getEmail());
        assertEquals("cat", secreteriat.getFirstname());
        assertEquals("cat", secreteriat.getLastname());
        assertTrue(secreteriat.getRegistrations().isEmpty());
    }

    @Test
    void testSecreteriatWithInvalidEmail() {
        secreteriat.setEmail("email");
        Set<ConstraintViolation<Secreteriat>> violations = validator.validateProperty(secreteriat, "email");
        assertFalse(violations.isEmpty(), "Invalid email should result validation error");
    }

    @Test
    void testSecreteriatWithEmptyFirstName() {
        secreteriat.setFirstname("");
        Set<ConstraintViolation<Secreteriat>> violations = validator.validateProperty(secreteriat, "firstname");
        assertFalse(violations.isEmpty(), "Empty first name should result validation error");
    }

    @Test
    void testSecreteriatWithNullLastName() {
        secreteriat.setLastname(null);
        Set<ConstraintViolation<Secreteriat>> violations = validator.validateProperty(secreteriat, "lastname");
        assertFalse(violations.isEmpty(), "Null last name should result validation error");
    }

    @Test
    void testValidSecreteriat() {
        Set<ConstraintViolation<Secreteriat>> violations = validator.validate(secreteriat);
        assertTrue(violations.isEmpty(), "No violations should be present fo valid secreteriat");
    }
}