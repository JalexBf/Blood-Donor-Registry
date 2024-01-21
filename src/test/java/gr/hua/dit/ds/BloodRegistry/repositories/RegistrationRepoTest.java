package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secreteriat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class RegistrationRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RegistrationRepository registrationRepository;


    @Test
    public void testFindByBloodDonor() {
        BloodDonor donor = createAndPersistBloodDonor();
        Registration registration = createAndPersistRegistration(donor, Status.APPROVED);

        entityManager.flush();

        Registration found = registrationRepository.findByBloodDonor(donor);

        assertThat(found).isNotNull();
        assertThat(found.getBloodDonor()).isEqualTo(donor);
        assertThat(found.getStatus()).isEqualTo(Status.APPROVED);
    }


    @Test
    public void testFindByStatus() {
        Registration registration1 = createAndPersistRegistration(createAndPersistBloodDonor(), Status.APPROVED);
        createAndPersistRegistration(createAndPersistBloodDonor(), Status.AWAITING);
        createAndPersistRegistration(createAndPersistBloodDonor(), Status.REJECTED);

        entityManager.flush();

        List<Registration> foundRegistrations = registrationRepository.findByStatus(Status.APPROVED);

        assertThat(foundRegistrations).hasSize(1);
        assertThat(foundRegistrations).containsExactly(registration1);
    }


    private BloodDonor createAndPersistBloodDonor() {
        BloodDonor donor = new BloodDonor();
        donor.setUsername("4atratwe");
        donor.setPassword("tre546564633");
        donor.setEmail("ttrwet@dwe.f");
        donor.setFirstname("rwq");
        donor.setLastname("Dwrqwqrwqrwq");
        donor.setRegion("rwerqr");
        donor.setPhone("5555555555");
        entityManager.persist(donor);
        return donor;
    }


    private Registration createAndPersistRegistration(BloodDonor donor, Status status) {
        Secreteriat secreteriat = createAndPersistSecreteriat();

        Registration registration = new Registration();
        registration.setBloodDonor(donor);
        registration.setSecreteriat(secreteriat); // Associate a secreteriat with the registration
        registration.setStatus(status);
        registration.setSubmissionDate(LocalDate.now());
        entityManager.persist(registration);

        return registration;
    }


    private Secreteriat createAndPersistSecreteriat() {
        Secreteriat secreteriat = new Secreteriat();

        secreteriat.setUsername("fdsaf");
        secreteriat.setEmail("fdsa@sfds.v");
        secreteriat.setPassword("fwe45445rddsa3");
        secreteriat.setLastname("fdsfsa");
        secreteriat.setFirstname("fdsrsq");

        // Persist secreteriat entity
        entityManager.persist(secreteriat);
        entityManager.flush();

        return secreteriat;
    }


    @Test
    public void testFindBySecreteriat() {
        Secreteriat secreteriat = createAndPersistSecreteriat();
        Registration registration1 = createAndPersistRegistration(createAndPersistBloodDonor(), secreteriat, Status.APPROVED);
        createAndPersistRegistration(createAndPersistBloodDonor(), createAndPersistSecreteriat(), Status.REJECTED);

        entityManager.flush();

        List<Registration> foundRegistrations = registrationRepository.findBySecreteriat(secreteriat);

        assertThat(foundRegistrations).hasSize(1);
        assertThat(foundRegistrations).containsExactly(registration1);
    }


    private Registration createAndPersistRegistration(BloodDonor donor, Secreteriat secreteriat, Status status) {
        Registration registration = new Registration();
        registration.setBloodDonor(donor);
        registration.setSecreteriat(secreteriat);
        registration.setStatus(status);
        registration.setSubmissionDate(LocalDate.now());
        entityManager.persist(registration);
        return registration;
    }
}
