package gr.hua.dit.ds.BloodRegistry.entities;

import gr.hua.dit.ds.BloodRegistry.entities.enums.BloodType;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Sex;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
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
class BloodDonorTest {

    private BloodDonor bloodDonor;
    private Validator validator;

    @BeforeEach
    void setUp() {
        bloodDonor = new BloodDonor();
        // test data
        bloodDonor.setUserId(1L);
        bloodDonor.setUsername("donoruser");
        bloodDonor.setPassword("password");
        bloodDonor.setEmail("donor@w.com");
        bloodDonor.setFirstname("what");
        bloodDonor.setLastname("ever");
        bloodDonor.setSex(Sex.MALE);
        bloodDonor.setBirthdate(LocalDate.of(1990, 1, 1));
        bloodDonor.setBloodType(BloodType.A_POSITIVE);
        bloodDonor.setAmka(1234567890L);
        bloodDonor.setRegion("region");
        bloodDonor.setPhone("1234567890");
        bloodDonor.setBloodworkFilePath("/path/bloodwork");
        bloodDonor.setLastDonationDate(LocalDate.now());

        // Validator setup
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, bloodDonor.getUserId());
        assertEquals("donoruser", bloodDonor.getUsername());
        assertEquals("password", bloodDonor.getPassword());
        assertEquals("donor@w.com", bloodDonor.getEmail());
        assertEquals("what", bloodDonor.getFirstname());
        assertEquals("ever", bloodDonor.getLastname());
        assertEquals(Sex.MALE, bloodDonor.getSex());
        assertEquals(LocalDate.of(1990, 1, 1), bloodDonor.getBirthdate());
        assertEquals(BloodType.A_POSITIVE, bloodDonor.getBloodType());
        assertEquals(1234567890L, bloodDonor.getAmka());
        assertEquals("region", bloodDonor.getRegion());
        assertEquals("1234567890", bloodDonor.getPhone());
        assertEquals("/path/bloodwork", bloodDonor.getBloodworkFilePath());
        assertEquals(LocalDate.now(), bloodDonor.getLastDonationDate());
    }

    @Test
    void testValidationConstraints() {
        BloodDonor invalidDonor = new BloodDonor();
        invalidDonor.setEmail("invalid");
        Set<ConstraintViolation<BloodDonor>> violations = validator.validate(invalidDonor);
        assertFalse(violations.isEmpty(), "Invalid email should result in a validation error");

        invalidDonor.setEmail("donor@w.com");
        invalidDonor.setAmka(0L);
        violations = validator.validate(invalidDonor);
        assertFalse(violations.isEmpty(), "Invalid AMKA should result in a validation error");
    }

    @Test
    void testValidBloodDonor() {
        Set<ConstraintViolation<BloodDonor>> violations = validator.validate(bloodDonor);
        assertTrue(violations.isEmpty(), "No violations should be present for a valid blood donor");
    }
}