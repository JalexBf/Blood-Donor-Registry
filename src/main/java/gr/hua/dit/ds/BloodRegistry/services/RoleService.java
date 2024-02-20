package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Roles;
import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.RoleRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Optional<Role> findRoleById(Long id) {
        return roleRepository.findById(id);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }


    // For scalability purposes
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found with id: " + id));
        roleRepository.delete(role);
    }


    // For scalability purposes
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }
}
