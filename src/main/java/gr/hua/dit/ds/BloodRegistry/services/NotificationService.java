package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {

    @Autowired
    private JavaMailSender emailSender;

    public void notifyDonor(BloodDonor donor) {
        String email = donor.getEmail();
        String message = buildNotificationMessage(donor);
        try {
            sendEmail(email, message);
        } catch (MailException e) {
            // Handle exception (log it, retry mechanism, etc.)
            System.out.println("Error sending email: " + e.getMessage());
        }
    }


    private String buildNotificationMessage(BloodDonor donor) {
        return "Dear " + donor.getFirstname() + " " + donor.getLastname() +
                ", you are now eligible to donate blood. Please schedule an appointment.";
    }


    private void sendEmail(String to, String messageText) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("it218142@hua.com");
        message.setTo(to);
        message.setSubject("Eligibility for Blood Donation");
        message.setText(messageText);
        emailSender.send(message);
    }
}
