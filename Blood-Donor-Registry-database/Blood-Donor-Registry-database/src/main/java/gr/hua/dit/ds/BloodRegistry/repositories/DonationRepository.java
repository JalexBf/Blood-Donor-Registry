package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;


@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<DonationRepository> findByBloodDonor(BloodDonor bloodDonor);

    List<DonationRepository> findByDonationDateBetween(LocalDate startDate, LocalDate endDate);
}
