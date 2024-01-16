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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
class BloodDonorTest {

    private BloodDonor bloodDonor;

    @BeforeEach
    void setUp() {
        bloodDonor = new BloodDonor();
        // Initialize with some test data
        bloodDonor.setUserId(1L);
        bloodDonor.setUsername("donorUser");
        bloodDonor.setPassword("donorPassword");
        bloodDonor.setEmail("donor@example.com");
        bloodDonor.setFirstname("John");
        bloodDonor.setLastname("Doe");
        bloodDonor.setSex(Sex.MALE);
        bloodDonor.setBirthdate(LocalDate.of(1990, 1, 1));
        bloodDonor.setBloodType(BloodType.A_POSITIVE);
        bloodDonor.setAmka(1234567890L);
        bloodDonor.setRegion("Region");
        bloodDonor.setPhone("1234567890");
        bloodDonor.setBloodworkFilePath("/path/to/bloodwork");
        bloodDonor.setLastDonationDate(LocalDate.now());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, bloodDonor.getUserId());
        assertEquals("donorUser", bloodDonor.getUsername());
        assertEquals(1L, bloodDonor.getUserId());
        assertEquals("donorUser", bloodDonor.getUsername());
        assertEquals("donorPassword", bloodDonor.getPassword());
        assertEquals("donor@example.com", bloodDonor.getEmail());
        assertEquals("John", bloodDonor.getFirstname());
        assertEquals("Doe", bloodDonor.getLastname());
        assertEquals(Sex.MALE, bloodDonor.getSex());
        assertEquals(LocalDate.of(1990, 1, 1), bloodDonor.getBirthdate());
        assertEquals(BloodType.A_POSITIVE, bloodDonor.getBloodType());
        assertEquals(1234567890L, bloodDonor.getAmka());
        assertEquals("Region", bloodDonor.getRegion());
        assertEquals("1234567890", bloodDonor.getPhone());
        assertEquals("/path/to/bloodwork", bloodDonor.getBloodworkFilePath());
        // Assuming the last donation date was set to the current date in setUp
        assertEquals(LocalDate.now(), bloodDonor.getLastDonationDate());
    }

    @Test
    void testValidationConstraints() {
        BloodDonor invalidDonor = new BloodDonor();
        invalidDonor.setEmail("invalidEmail");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BloodDonor>> violations = validator.validate(invalidDonor);

        assertFalse(violations.isEmpty()); // Expecting validation errors
    }
}