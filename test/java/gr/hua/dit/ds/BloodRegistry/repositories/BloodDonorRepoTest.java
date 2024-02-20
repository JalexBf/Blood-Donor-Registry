package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.enums.BloodType;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Sex;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
class BloodDonorRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BloodDonorRepository bloodDonorRepository;

    @BeforeEach
    void setUp() {
        BloodDonor donor1 = new BloodDonor();
        donor1.setUsername("trew");
        donor1.setPassword("password123");
        donor1.setEmail("john.doe@example.com");
        donor1.setFirstname("John");
        donor1.setLastname("Doe");
        donor1.setSex(Sex.MALE);
        donor1.setBirthdate(LocalDate.of(1990, 1, 1));
        donor1.setBloodType(BloodType.A_POSITIVE);
        donor1.setAmka(1234567890L);
        donor1.setRegion("Athens");
        donor1.setPhone("2101234567");
        bloodDonorRepository.save(donor1);

        BloodDonor donor2 = new BloodDonor();
        donor2.setUsername("jane.doe");
        donor2.setPassword("password456");
        donor2.setEmail("jane.doe@example.com");
        donor2.setFirstname("Jane");
        donor2.setLastname("Doe");
        donor2.setSex(Sex.FEMALE);
        donor2.setBirthdate(LocalDate.of(1995, 2, 2));
        donor2.setBloodType(BloodType.B_POSITIVE);
        donor2.setAmka(1234567891L);
        donor2.setRegion("Athens");
        donor2.setPhone("2101234568");
        bloodDonorRepository.save(donor2);
    }

    @Test
    void findByBloodtype_ShouldReturnDonorsWithSpecifiedBloodType() {
        List<BloodDonor> donors = bloodDonorRepository.findByBloodtype(BloodType.A_POSITIVE);
        Assertions.assertFalse(donors.isEmpty());
        Assertions.assertTrue(donors.stream().allMatch(donor -> donor.getBloodType() == BloodType.A_POSITIVE));
    }

    @Test
    void findByRegion_ShouldReturnDonorsFromSpecifiedRegion() {
        List<BloodDonor> donors = bloodDonorRepository.findByRegion("Athens");
        Assertions.assertFalse(donors.isEmpty());
        Assertions.assertTrue(donors.stream().allMatch(donor -> "Athens".equals(donor.getRegion())));
    }
}
