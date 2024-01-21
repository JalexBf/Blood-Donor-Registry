package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.model.Secreteriat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface SecreteriatRepository extends JpaRepository<Secreteriat, Long> {

    List<Secreteriat> findByFirstname(String firstname);

}
