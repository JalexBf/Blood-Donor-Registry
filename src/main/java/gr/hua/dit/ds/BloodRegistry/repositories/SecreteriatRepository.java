package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.model.Secretariat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface SecreteriatRepository extends JpaRepository<Secretariat, Long> {

    List<Secretariat> findByFirstname(String firstname);

    Optional<Secretariat> findByUserId(Long id);

}
