package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private NotificationService notificationService;

    private BloodDonor donor;

    @BeforeEach
    void setUp() {
        donor = new BloodDonor();
        donor.setFirstname("John");
        donor.setLastname("Doe");
        donor.setEmail("johndoe@example.com");
        // You can initialize other necessary fields of BloodDonor here
    }

    @Test
    void testNotifyDonor() {
        doNothing().when(emailSender).send(any(SimpleMailMessage.class));
        notificationService.notifyDonor(donor);
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }


    @Test
    void testNotifyDonorWithException() {
        doThrow(new MailException("Exception for test") {}).when(emailSender).send(any(SimpleMailMessage.class));

        // You can also verify that the exception is handled (e.g., logged)
        notificationService.notifyDonor(donor);
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }


}
