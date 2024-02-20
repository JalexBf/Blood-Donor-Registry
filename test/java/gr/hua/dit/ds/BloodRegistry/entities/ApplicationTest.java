package gr.hua.dit.ds.BloodRegistry.entities;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.Application;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secretariat;
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
class ApplicationTest {

    private Application application;
    private BloodDonor bloodDonor;
    private Secretariat Secretariat;
    private Validator validator;

    @BeforeEach
    void setUp() {
        bloodDonor = new BloodDonor();
        Secretariat = new Secretariat();
        application = new Application();

        application.setapplicationId(1L);
        application.setStatus(Status.APPROVED);
        application.setSubmissionDate(LocalDate.now());
        application.setBloodDonor(bloodDonor);
        application.setSecretariat(Secretariat);

        // Validator setup
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, application.getapplicationId());
        assertEquals(Status.APPROVED, application.getStatus());
        assertEquals(LocalDate.now(), application.getSubmissionDate());
        assertEquals(bloodDonor, application.getBloodDonor());
        assertEquals(Secretariat, application.getSecretariat());
    }

    @Test
    void testapplicationValidationConstraints() {
        Application invalidApplication = new Application();
        invalidApplication.setSubmissionDate(null); // SubmissionDate is @NotNull
        Set<ConstraintViolation<Application>> violations = validator.validate(invalidApplication);
        assertFalse(violations.isEmpty(), "Missing submission date should result in a validation error");
    }

    @Test
    void testValidapplication() {
        Set<ConstraintViolation<Application>> violations = validator.validate(application);
        assertTrue(violations.isEmpty(), "No violations should be present for a valid application");
    }
}
