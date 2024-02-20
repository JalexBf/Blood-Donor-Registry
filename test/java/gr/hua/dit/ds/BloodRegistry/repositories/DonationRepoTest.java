package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Donation;
import gr.hua.dit.ds.BloodRegistry.entities.enums.BloodType;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DonationRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DonationRepository donationRepository;

    private BloodDonor validBloodDonor;

    @BeforeEach
    public void setUp() {
        validBloodDonor = new BloodDonor();
        validBloodDonor.setFirstname("WQE");
        validBloodDonor.setLastname("eqwew");
        validBloodDonor.setSex(Sex.MALE);
        validBloodDonor.setBirthdate(LocalDate.of(2000, 1, 1));
        validBloodDonor.setBloodType(BloodType.A_POSITIVE);
        validBloodDonor.setAmka(3344444444L);
        validBloodDonor.setRegion("DWDDW");
        validBloodDonor.setPhone("1234567890");
        validBloodDonor.setEmail("johndoe@example.com");
        validBloodDonor.setUsername("johndoe");
        validBloodDonor.setPassword("password");
        entityManager.persistAndFlush(validBloodDonor);
    }

    @Test
    public void testFindByBloodDonor() {
        Donation donation = new Donation();
        donation.setDonationDate(LocalDate.now());
        donation.setBloodDonor(validBloodDonor);
        entityManager.persistAndFlush(donation);

        List<Donation> foundDonations = donationRepository.findByBloodDonor(validBloodDonor);
        assertThat(foundDonations).isNotEmpty();
        assertThat(foundDonations.get(0).getBloodDonor()).isEqualTo(validBloodDonor);
    }

    @Test
    public void testFindByDonationDateBetween() {
        LocalDate startDate = LocalDate.now().minusDays(10);
        LocalDate endDate = LocalDate.now();
        Donation donation = new Donation();
        donation.setDonationDate(LocalDate.now().minusDays(5));
        donation.setBloodDonor(validBloodDonor);
        entityManager.persistAndFlush(donation);

        List<Donation> foundDonations = donationRepository.findByDonationDateBetween(startDate, endDate);
        assertThat(foundDonations).isNotEmpty();
        assertThat(foundDonations.get(0).getDonationDate()).isBetween(startDate, endDate);
    }

}
