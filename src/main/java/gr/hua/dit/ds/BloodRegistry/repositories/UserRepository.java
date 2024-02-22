package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(Long userId);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findAllByRoles(Role role);

}
