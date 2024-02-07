package gr.hua.dit.ds.BloodRegistry.schedulers;

import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.services.BloodDonorService;
import gr.hua.dit.ds.BloodRegistry.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class DonationEligibilityScheduler {

    @Autowired
    private BloodDonorService bloodDonorService;

    @Autowired
    private NotificationService notificationService; // Assuming this service exists

    @Scheduled(cron = "0 0 0 * * ?") // Adjust the cron expression as needed
    public void checkAndNotifyEligibleDonors() {
        List<BloodDonor> eligibleDonors = bloodDonorService.findEligibleDonorsForNotification();
        eligibleDonors.forEach(donor -> {
            notificationService.notifyDonor(donor); // Implement this method to send notifications
        });
    }
}
