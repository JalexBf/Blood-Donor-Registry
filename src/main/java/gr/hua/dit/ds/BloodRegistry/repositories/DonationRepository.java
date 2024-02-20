package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    // Assuming BloodDonor has an 'id' field (inherited from User)
    Optional<Donation> findById(Long userId);

    List<Donation> findByDonationDateBetween(LocalDate startDate, LocalDate endDate);
}




