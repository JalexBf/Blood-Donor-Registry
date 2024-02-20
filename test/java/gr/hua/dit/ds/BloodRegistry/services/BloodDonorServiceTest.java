package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Sex;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.repositories.BloodDonorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BloodDonorServiceTest {

    @Mock
    private BloodDonorRepository bloodDonorRepository;

    @InjectMocks
    private BloodDonorService bloodDonorService;

    private BloodDonor maleDonor, femaleDonor;

    @BeforeEach
    public void setup() {
        maleDonor = new BloodDonor();
        femaleDonor = new BloodDonor();

        maleDonor.setUserId(1L);
        maleDonor.setSex(Sex.MALE);
        maleDonor.setLastDonationDate(LocalDate.of(2024, 1, 15)); // Donated less than 3 months ago

        femaleDonor.setUserId(2L);
        femaleDonor.setSex(Sex.FEMALE);
        femaleDonor.setLastDonationDate(LocalDate.of(2023, 8, 30)); // Donated more than 4 months ago
    }

    @Test
    public void testFindEligibleDonorsForNotification() {
        when(bloodDonorRepository.findAll()).thenReturn(Arrays.asList(maleDonor, femaleDonor));

        List<BloodDonor> eligibleDonors = bloodDonorService.findEligibleDonorsForNotification();

        assertNotNull(eligibleDonors);
        assertTrue(eligibleDonors.contains(femaleDonor), "Female donor should be eligible");
        assertFalse(eligibleDonors.contains(maleDonor), "Male donor should not be eligible");

        verify(bloodDonorRepository).findAll();
    }

    @Test
    public void testIsEligibleForDonation() {
        // Example test case for a male donor
        BloodDonor testMaleDonor = new BloodDonor();
        testMaleDonor.setUserId(3L);
        testMaleDonor.setSex(Sex.MALE);
        testMaleDonor.setLastDonationDate(LocalDate.of(2024, 1, 1));

        boolean maleEligibility = bloodDonorService.isEligibleForDonation(testMaleDonor, LocalDate.of(2024, 4, 1));
        assertFalse(maleEligibility, "Male donor should not be eligible");

        // Example test case for a female donor
        BloodDonor testFemaleDonor = new BloodDonor();
        testFemaleDonor.setUserId(4L);
        testFemaleDonor.setSex(Sex.FEMALE);
        testFemaleDonor.setLastDonationDate(LocalDate.of(2023, 11, 1));

        boolean femaleEligibility = bloodDonorService.isEligibleForDonation(testFemaleDonor, LocalDate.of(2024, 4, 1));
        assertTrue(femaleEligibility, "Female donor should be eligible");
    }
}
