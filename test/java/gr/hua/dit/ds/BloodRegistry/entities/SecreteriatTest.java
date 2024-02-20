package gr.hua.dit.ds.BloodRegistry.entities;

import gr.hua.dit.ds.BloodRegistry.entities.model.Application;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secretariat;
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
class SecretariatTest {

    private Secretariat Secretariat;
    private Validator validator;

    @BeforeEach
    void setUp() {
        Secretariat = new Secretariat();
        Secretariat.setUserId(1L);
        Secretariat.setUsername("user");
        Secretariat.setPassword("123456789");
        Secretariat.setEmail("max@a.com");

        Secretariat.setFirstname("cat");
        Secretariat.setLastname("cat");

        Secretariat.setAccountNonExpired(true);
        Secretariat.setAccountNonLocked(true);
        Secretariat.setCredentialsNonExpired(true);
        Secretariat.setEnabled(true);
        List<Application> applications = new ArrayList<>();
        Secretariat.setApplications(applications);

        // Validator setup
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, Secretariat.getUserId());
        assertEquals("user", Secretariat.getUsername());
        assertEquals("123456789", Secretariat.getPassword());
        assertEquals("max@a.com", Secretariat.getEmail());
        assertEquals("cat", Secretariat.getFirstname());
        assertEquals("cat", Secretariat.getLastname());
        assertTrue(Secretariat.getApplications().isEmpty());
    }

    @Test
    void testSecretariatWithInvalidEmail() {
        Secretariat.setEmail("email");
        Set<ConstraintViolation<Secretariat>> violations = validator.validateProperty(Secretariat, "email");
        assertFalse(violations.isEmpty(), "Invalid email should result validation error");
    }

    @Test
    void testSecretariatWithEmptyFirstName() {
        Secretariat.setFirstname("");
        Set<ConstraintViolation<Secretariat>> violations = validator.validateProperty(Secretariat, "firstname");
        assertFalse(violations.isEmpty(), "Empty first name should result validation error");
    }

    @Test
    void testSecretariatWithNullLastName() {
        Secretariat.setLastname(null);
        Set<ConstraintViolation<Secretariat>> violations = validator.validateProperty(Secretariat, "lastname");
        assertFalse(violations.isEmpty(), "Null last name should result validation error");
    }

    @Test
    void testValidSecretariat() {
        Set<ConstraintViolation<Secretariat>> violations = validator.validate(Secretariat);
        assertTrue(violations.isEmpty(), "No violations should be present fo valid Secretariat");
    }
}