package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.RoleRepository;
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

<<<<<<< HEAD
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }


=======
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
>>>>>>> backup-2b0394c
    @Transactional
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Role updateRole(Role role) {
        Role existingRole = roleRepository.findById(role.getRoleId())
                .orElseThrow(() -> new NotFoundException("Role not found with id: " + role.getRoleId()));
        existingRole.setName(role.getName());
        existingRole.setPermissions(role.getPermissions());
        return roleRepository.save(existingRole);
    }

    public Optional<Role> findRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Transactional
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role not found with id: " + id));
        roleRepository.delete(role);
    }
}
