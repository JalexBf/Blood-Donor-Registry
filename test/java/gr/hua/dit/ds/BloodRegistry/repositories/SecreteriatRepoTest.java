package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.model.Secretariat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class SecretariatRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SecretariatRepository secretariatRepository;

    @Test
    public void testCreateAndRetrieveSecretariat() {
        // Simulate admin creating a new Secretariat account
        Secretariat newSecretariat = createSecretariat("admin_secret", "admin_pass", "admin_secret@em.com", "Admin", "Sec");
        Secretariat savedSecretariat = secretariatRepository.save(newSecretariat);

        // Verify creation
        assertNotNull(savedSecretariat.getUserId());
        assertEquals("Admin", savedSecretariat.getFirstname());

        // Simulate admin retrieving an existing Secretariat account
        Optional<Secretariat> fetchedSecretariat = secretariatRepository.findById(savedSecretariat.getUserId());
        assertTrue(fetchedSecretariat.isPresent());
        assertEquals("Admin", fetchedSecretariat.get().getFirstname());
    }

    private Secretariat createSecretariat(String username, String password, String email, String firstname, String lastname) {
        Secretariat Secretariat = new Secretariat();
        Secretariat.setUsername(username);
        Secretariat.setPassword(password);
        Secretariat.setEmail(email);
        Secretariat.setFirstname(firstname);
        Secretariat.setLastname(lastname);
        return Secretariat;
    }
}
