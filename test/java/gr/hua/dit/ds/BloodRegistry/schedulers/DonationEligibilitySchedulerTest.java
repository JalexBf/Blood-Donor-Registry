package gr.hua.dit.ds.BloodRegistry.schedulers;

import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Sex;
import gr.hua.dit.ds.BloodRegistry.services.BloodDonorService;
import gr.hua.dit.ds.BloodRegistry.services.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import java.time.LocalDate;


@ExtendWith(MockitoExtension.class)
public class DonationEligibilitySchedulerTest {

    @Mock
    private BloodDonorService bloodDonorService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private DonationEligibilityScheduler eligibilityScheduler;

    private BloodDonor eligibleDonor;


    @BeforeEach
    public void setup() {
        eligibleDonor = new BloodDonor();
        eligibleDonor.setSex(Sex.MALE);
        eligibleDonor.setLastDonationDate(LocalDate.now().minusMonths(4));
    }


    @Test
    public void testCheckAndNotifyEligibleDonors() {
        List<BloodDonor> eligibleDonors = Arrays.asList(eligibleDonor);
        when(bloodDonorService.findEligibleDonorsForNotification()).thenReturn(eligibleDonors);

        eligibilityScheduler.checkAndNotifyEligibleDonors();

        verify(bloodDonorService).findEligibleDonorsForNotification();
        verify(notificationService).notifyDonor(eligibleDonor);
    }
}
