package gr.hua.dit.ds.BloodRegistry.entities;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secreteriat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class RegistrationTest {

    private Registration registration;
    private BloodDonor bloodDonor;
    private Secreteriat secreteriat;

    @BeforeEach
    void setUp() {
        bloodDonor = new BloodDonor();
        secreteriat = new Secreteriat();
        registration = new Registration();

        registration.setRegistrationId(1L);
        registration.setStatus(Status.APPROVED);
        registration.setSubmissionDate(LocalDate.now());
        registration.setBloodDonor(bloodDonor);
        registration.setSecreteriat(secreteriat);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, registration.getRegistrationId());
        assertEquals(Status.APPROVED, registration.getStatus());
        assertEquals(LocalDate.now(), registration.getSubmissionDate());
        assertEquals(bloodDonor, registration.getBloodDonor());
        assertEquals(secreteriat, registration.getSecreteriat());
    }
}
