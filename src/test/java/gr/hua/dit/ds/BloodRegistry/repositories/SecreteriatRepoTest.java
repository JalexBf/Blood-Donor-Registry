package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.model.Secreteriat;
import gr.hua.dit.ds.BloodRegistry.repositories.SecreteriatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
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


    @BeforeEach
    public void setUp() {
        Secreteriat secreteriat = new Secreteriat();
        secreteriat.setUsername("user");
        secreteriat.setPassword("pass123456");
        secreteriat.setEmail("secret@em.com");
        secreteriat.setFirstname("John");
        secreteriat.setLastname("Doe");
        entityManager.persist(secreteriat);
    }


    @Test
    public void testSaveSecreteriat() {
        Secreteriat newSecreteriat = new Secreteriat();
        newSecreteriat.setUsername("new1234567");
        newSecreteriat.setPassword("new_123456");
        newSecreteriat.setEmail("new_secret@em.com");
        newSecreteriat.setFirstname("Jane");
        newSecreteriat.setLastname("Doe");

        Secreteriat savedSecreteriat = secreteriatRepository.save(newSecreteriat);
        assertNotNull(savedSecreteriat.getUserId()); // Corrected to getUserId
        assertEquals("Jane", savedSecreteriat.getFirstname());
    }


    @Test
    public void testFindSecreteriatById() {
        Secreteriat newSecreteriat = new Secreteriat();
        newSecreteriat.setUsername("new_secret");
        newSecreteriat.setPassword("new_pass");
        newSecreteriat.setEmail("new_secret@example.com");
        newSecreteriat.setFirstname("Jane");
        newSecreteriat.setLastname("Doe");

        Secreteriat savedSecreteriat = secreteriatRepository.save(newSecreteriat);

        // Ensure entity is saved
        assertNotNull(savedSecreteriat.getUserId());
        // Fetch the entity using the saved ID
        Optional<Secreteriat> fetchedSecreteriat = secreteriatRepository.findById(savedSecreteriat.getUserId());
        // Ensure the fetched entity is not null and properties match
        assertTrue(fetchedSecreteriat.isPresent());
        assertEquals("Jane", fetchedSecreteriat.get().getFirstname());
    }


    // Clean up after tests
    @AfterEach
    public void tearDown() {
        entityManager.clear();
    }
}
