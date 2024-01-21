package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> backup-2b0394c
import java.util.Optional;

@Repository
@Hidden
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

<<<<<<< HEAD
=======
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findAllByRole(Role donorRole);
>>>>>>> backup-2b0394c
}
