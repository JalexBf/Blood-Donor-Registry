package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secreteriat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    Registration findByBloodDonor(BloodDonor bloodDonor);

    List<Registration> findByStatus(Status status);

    List<Registration> findBySecreteriat(Secreteriat secreteriat);
}
