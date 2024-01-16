package gr.hua.dit.ds.BloodRegistry.entities;

import gr.hua.dit.ds.BloodRegistry.entities.enums.BloodType;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Sex;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Donation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class DonationTest {

    private Donation donation;
    private BloodDonor bloodDonor;

    @BeforeEach
    void setUp() {
        // Create a BloodDonor instance for testing
        bloodDonor = new BloodDonor();
        bloodDonor.setUserId(1L);
        bloodDonor.setUsername("donorUsername");
        bloodDonor.setPassword("donorPassword");
        bloodDonor.setEmail("donor@example.com");
        bloodDonor.setFirstname("John");
        bloodDonor.setLastname("Doe");
        bloodDonor.setSex(Sex.MALE);
        bloodDonor.setBirthdate(LocalDate.of(1980, 1, 1));
        bloodDonor.setBloodType(BloodType.A_POSITIVE);
        bloodDonor.setAmka(1234567890L);
        bloodDonor.setRegion("Region");
        bloodDonor.setPhone("1234567890");

        // Initialize the Donation instance
        donation = new Donation();
        donation.setDonationId(1L);
        donation.setDonationDate(LocalDate.now());
        donation.setBloodDonor(bloodDonor);
    }

    @Test
    void getDonationId() {
        assertEquals(1L, donation.getDonationId());
    }

    @Test
    void getDonationDate() {
        assertEquals(LocalDate.now(), donation.getDonationDate());
    }

    @Test
    void getBloodDonor() {
        assertEquals(bloodDonor, donation.getBloodDonor());
    }
}
