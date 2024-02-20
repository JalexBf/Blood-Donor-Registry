package gr.hua.dit.ds.BloodRegistry.entities;

import gr.hua.dit.ds.BloodRegistry.entities.enums.BloodType;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Sex;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Donation;
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
class DonationTest {

    private Donation donation;
    private BloodDonor bloodDonor;
    private Validator validator;

    @BeforeEach
    void setUp() {
        bloodDonor = new BloodDonor();
        bloodDonor.setUserId(1L);
        bloodDonor.setUsername("donor");
        bloodDonor.setPassword("e5fse55eee");
        bloodDonor.setEmail("tredd@yryt.t");
        bloodDonor.setFirstname("my");
        bloodDonor.setLastname("name");
        bloodDonor.setSex(Sex.MALE);
        bloodDonor.setBirthdate(LocalDate.of(1980, 1, 1));
        bloodDonor.setBloodType(BloodType.A_POSITIVE);
        bloodDonor.setAmka(1234567890L);
        bloodDonor.setRegion("region");
        bloodDonor.setPhone("12345678999");

        donation = new Donation();
        donation.setDonationId(1L);
        donation.setDonationDate(LocalDate.now());
        donation.setBloodDonor(bloodDonor);

        // Validator setup
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, donation.getDonationId());
        assertEquals(LocalDate.now(), donation.getDonationDate());
        assertEquals(bloodDonor, donation.getBloodDonor());
    }

    @Test
    void testDonationValidationConstraints() {
        Donation invalidDonation = new Donation();
        invalidDonation.setDonationDate(null);
        Set<ConstraintViolation<Donation>> violations = validator.validate(invalidDonation);
        assertFalse(violations.isEmpty(), "Missing donation date -validation error");
    }

    @Test
    void testValidDonation() {
        Set<ConstraintViolation<Donation>> violations = validator.validate(donation);
        assertTrue(violations.isEmpty(), "No violations should be present for valid donation");
    }
}
