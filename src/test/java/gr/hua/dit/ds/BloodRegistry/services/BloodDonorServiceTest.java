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

    private BloodDonor maleDonor, femaleDonor; // Assuming BloodDonor class has these fields

    @BeforeEach
    public void setup() {
        maleDonor = new BloodDonor();
        femaleDonor = new BloodDonor();

        // Assuming setters for sex, lastDonationDate, etc., exist
        maleDonor.setSex(Sex.MALE);
        maleDonor.setLastDonationDate(LocalDate.now().minusMonths(4)); // Male donor last donated 4 months ago

        femaleDonor.setSex(Sex.FEMALE);
        femaleDonor.setLastDonationDate(LocalDate.now().minusMonths(5)); // Female donor last donated 5 months ago
    }

    @Test
    public void testFindEligibleDonorsForNotification() {
        // Mocking the repository to return a list of donors
        when(bloodDonorRepository.findAll()).thenReturn(Arrays.asList(maleDonor, femaleDonor));

        List<BloodDonor> eligibleDonors = bloodDonorService.findEligibleDonorsForNotification();

        // Assertions
        assertNotNull(eligibleDonors);
        assertTrue(eligibleDonors.contains(maleDonor));
        assertFalse(eligibleDonors.contains(femaleDonor)); // Female donor is not yet eligible

        // Verify interaction with the repository
        verify(bloodDonorRepository).findAll();
    }

    // Other tests for methods like isEligibleForDonation, etc.
}
