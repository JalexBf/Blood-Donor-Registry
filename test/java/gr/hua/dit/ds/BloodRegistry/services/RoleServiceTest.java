package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private Role role;

    @BeforeEach
    public void setup() {
        role = new Role();
        role.setRoleId(1L);
        role.setName("ROLE_TEST");
    }

    @Test
    public void testFindByName() {
        when(roleRepository.findByName("ROLE_TEST")).thenReturn(Optional.of(role));
        Optional<Role> found = roleService.findByName("ROLE_TEST");
        assertTrue(found.isPresent());
        assertEquals(role.getName(), found.get().getName());
        verify(roleRepository).findByName("ROLE_TEST");
    }

    @Test
    public void testFindByName_NotFound() {
        when(roleRepository.findByName("ROLE_UNKNOWN")).thenReturn(Optional.empty());
        Optional<Role> found = roleService.findByName("ROLE_UNKNOWN");
        assertFalse(found.isPresent());
        verify(roleRepository).findByName("ROLE_UNKNOWN");
    }

    @Test
    public void testCreateRole() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        Role created = roleService.createRole(role);
        assertNotNull(created);
        assertEquals(role.getName(), created.getName());
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    public void testFindRoleById() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        Optional<Role> found = roleService.findRoleById(1L);
        assertTrue(found.isPresent());
        assertEquals(role.getRoleId(), found.get().getRoleId());
        verify(roleRepository).findById(1L);
    }

    @Test
    public void testFindRoleById_NotFound() {
        Long roleId = 2L;
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());
        Optional<Role> found = roleService.findRoleById(roleId);
        assertFalse(found.isPresent());
        verify(roleRepository).findById(roleId);
    }

    @Test
    public void testDeleteRole() {
        Long roleId = 1L;
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        doNothing().when(roleRepository).delete(any(Role.class));
        roleService.deleteRole(roleId);
        verify(roleRepository).findById(roleId);
        verify(roleRepository).delete(any(Role.class));
    }


    @Test
    public void testDeleteRoleNotFound() {
        Long roleId = 2L;
        when(roleRepository.findById(roleId)).thenThrow(new NotFoundException("Role not found with id: " + roleId));
        assertThrows(NotFoundException.class, () -> roleService.deleteRole(roleId));
        verify(roleRepository).findById(roleId);
    }
}
