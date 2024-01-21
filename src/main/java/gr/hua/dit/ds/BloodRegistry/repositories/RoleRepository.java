package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

<<<<<<< HEAD
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
=======

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

>>>>>>> backup-2b0394c
    Optional<Role> findByName(String name);
}

