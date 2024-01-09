package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    RegistrationRepository findByBloodDonor(BloodDonor bloodDonor);

    List<RegistrationRepository> findByStatus(Status status);


}
