package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.Application;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secretariat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class ApplicationRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicationRepository applicationRepository;


    @Test
    public void testFindByBloodDonor() {
        BloodDonor donor = createAndPersistBloodDonor();
        Application application = createAndPersistapplication(donor, Status.APPROVED);

        entityManager.flush();

        Application found = applicationRepository.findByBloodDonor(donor);

        assertThat(found).isNotNull();
        assertThat(found.getBloodDonor()).isEqualTo(donor);
        assertThat(found.getStatus()).isEqualTo(Status.APPROVED);
    }


    @Test
    public void testFindByStatus() {
        Application application1 = createAndPersistapplication(createAndPersistBloodDonor(), Status.APPROVED);
        createAndPersistapplication(createAndPersistBloodDonor(), Status.AWAITING);
        createAndPersistapplication(createAndPersistBloodDonor(), Status.REJECTED);

        entityManager.flush();

        List<Application> foundApplications = applicationRepository.findByStatus(Status.APPROVED);

        assertThat(foundApplications).hasSize(1);
        assertThat(foundApplications).containsExactly(application1);
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


    private Application createAndPersistapplication(BloodDonor donor, Status status) {
        Secretariat Secretariat = createAndPersistSecretariat();

        Application application = new Application();
        application.setBloodDonor(donor);
        application.setSecretariat(Secretariat); // Associate a Secretariat with the application
        application.setStatus(status);
        application.setSubmissionDate(LocalDate.now());
        entityManager.persist(application);

        return application;
    }


    private Secretariat createAndPersistSecretariat() {
        Secretariat Secretariat = new Secretariat();

        Secretariat.setUsername("fdsaf");
        Secretariat.setEmail("fdsa@sfds.v");
        Secretariat.setPassword("fwe45445rddsa3");
        Secretariat.setLastname("fdsfsa");
        Secretariat.setFirstname("fdsrsq");

        // Persist Secretariat entity
        entityManager.persist(Secretariat);
        entityManager.flush();

        return Secretariat;
    }


    @Test
    public void testFindBySecretariat() {
        Secretariat Secretariat = createAndPersistSecretariat();
        Application application1 = createAndPersistapplication(createAndPersistBloodDonor(), Secretariat, Status.APPROVED);
        createAndPersistapplication(createAndPersistBloodDonor(), createAndPersistSecretariat(), Status.REJECTED);

        entityManager.flush();

        List<Application> foundApplications = applicationRepository.findBySecretariat(Secretariat);

        assertThat(foundApplications).hasSize(1);
        assertThat(foundApplications).containsExactly(application1);
    }


    private Application createAndPersistapplication(BloodDonor donor, Secretariat Secretariat, Status status) {
        Application application = new Application();
        application.setBloodDonor(donor);
        application.setSecretariat(Secretariat);
        application.setStatus(status);
        application.setSubmissionDate(LocalDate.now());
        entityManager.persist(application);
        return application;
    }
}
