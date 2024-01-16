package gr.hua.dit.ds.BloodRegistry.entities;

import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secreteriat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SecreteriatTest {

    private Secreteriat secreteriat;

    @BeforeEach
    void setUp() {
        secreteriat = new Secreteriat();
        secreteriat.setUserId(1L);
        secreteriat.setUsername("secretariatUsername");
        secreteriat.setPassword("secretariatPassword");
        secreteriat.setEmail("secretariat@example.com");
        secreteriat.setFirstname("SecretariatFirstName");
        secreteriat.setLastname("SecretariatLastName");

        List<Registration> registrations = new ArrayList<>();
        secreteriat.setRegistrations(registrations);
    }


    @Test
    void testGettersAndSetters() {
        assertEquals(1L, secreteriat.getUserId());
        assertEquals("secretariatUsername", secreteriat.getUsername());
        assertEquals("secretariatPassword", secreteriat.getPassword());
        assertEquals("secretariat@example.com", secreteriat.getEmail());
        assertEquals("SecretariatFirstName", secreteriat.getFirstname());
        assertEquals("SecretariatLastName", secreteriat.getLastname());
        assertTrue(secreteriat.getRegistrations().isEmpty()); // Or specific checks if registrations are added
    }
}
