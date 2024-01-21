package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.model.Secreteriat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class SecreteriatRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SecreteriatRepository secreteriatRepository;

    @Test
    public void testCreateAndRetrieveSecreteriat() {
        // Simulate admin creating a new secreteriat account
        Secreteriat newSecreteriat = createSecreteriat("admin_secret", "admin_pass", "admin_secret@em.com", "Admin", "Sec");
        Secreteriat savedSecreteriat = secreteriatRepository.save(newSecreteriat);

        // Verify creation
        assertNotNull(savedSecreteriat.getUserId());
        assertEquals("Admin", savedSecreteriat.getFirstname());

        // Simulate admin retrieving an existing secreteriat account
        Optional<Secreteriat> fetchedSecreteriat = secreteriatRepository.findById(savedSecreteriat.getUserId());
        assertTrue(fetchedSecreteriat.isPresent());
        assertEquals("Admin", fetchedSecreteriat.get().getFirstname());
    }

    private Secreteriat createSecreteriat(String username, String password, String email, String firstname, String lastname) {
        Secreteriat secreteriat = new Secreteriat();
        secreteriat.setUsername(username);
        secreteriat.setPassword(password);
        secreteriat.setEmail(email);
        secreteriat.setFirstname(firstname);
        secreteriat.setLastname(lastname);
        return secreteriat;
    }
}
