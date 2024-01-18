package gr.hua.dit.ds.BloodRegistry.entities;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secreteriat;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class RegistrationTest {

    private Registration registration;
    private BloodDonor bloodDonor;
    private Secreteriat secreteriat;
    private Validator validator;

    @BeforeEach
    void setUp() {
        bloodDonor = new BloodDonor();
        secreteriat = new Secreteriat();
        registration = new Registration();

        registration.setRegistrationId(1L);
        registration.setStatus(Status.APPROVED);
        registration.setSubmissionDate(LocalDate.now());
        registration.setBloodDonor(bloodDonor);
        registration.setSecreteriat(secreteriat);

        // Validator setup
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, registration.getRegistrationId());
        assertEquals(Status.APPROVED, registration.getStatus());
        assertEquals(LocalDate.now(), registration.getSubmissionDate());
        assertEquals(bloodDonor, registration.getBloodDonor());
        assertEquals(secreteriat, registration.getSecreteriat());
    }

    @Test
    void testRegistrationValidationConstraints() {
        Registration invalidRegistration = new Registration();
        invalidRegistration.setSubmissionDate(null); // SubmissionDate is @NotNull
        Set<ConstraintViolation<Registration>> violations = validator.validate(invalidRegistration);
        assertFalse(violations.isEmpty(), "Missing submission date should result in a validation error");
    }

    @Test
    void testValidRegistration() {
        Set<ConstraintViolation<Registration>> violations = validator.validate(registration);
        assertTrue(violations.isEmpty(), "No violations should be present for a valid registration");
    }
}
