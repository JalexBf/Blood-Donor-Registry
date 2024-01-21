package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.enums.BloodType;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BloodDonorRepository extends JpaRepository<BloodDonor, Long> {

    List<BloodDonor> findByBloodtype(BloodType bloodtype); // Corrected method name

    List<BloodDonor> findByRegion(String region);
}
