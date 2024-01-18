package gr.hua.dit.ds.BloodRegistry.repositories;

import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Permissions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class RoleRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateAndFindRole() {
        Role role = new Role();
        role.setName("ROLE_TEST");
        role.setPermissions(EnumSet.of(Permissions.READ_USER));

        role = entityManager.persistFlushFind(role);
        Role foundRole = entityManager.find(Role.class, role.getRoleId());

        assertNotNull(foundRole);
        assertEquals("ROLE_TEST", foundRole.getName());
        assertTrue(foundRole.getPermissions().contains(Permissions.READ_USER));
    }

    @Test
    public void testUpdateRole() {
        Role role = new Role();
        role.setName("ROLE_UPDATE_TEST");
        role.setPermissions(EnumSet.of(Permissions.READ_USER));

        role = entityManager.persistFlushFind(role);
        role.setName("ROLE_UPDATED");
        role.setPermissions(EnumSet.of(Permissions.WRITE_USER));

        Role updatedRole = entityManager.persistFlushFind(role);
        assertEquals("ROLE_UPDATED", updatedRole.getName());
        assertTrue(updatedRole.getPermissions().contains(Permissions.WRITE_USER));
    }

    @Test
    public void testDeleteRole() {
        Role role = new Role();
        role.setName("ROLE_DELETE_TEST");
        role.setPermissions(EnumSet.of(Permissions.DELETE_USER));

        role = entityManager.persistFlushFind(role);
        Long roleId = role.getRoleId();

        entityManager.remove(entityManager.find(Role.class, roleId));
        entityManager.flush();

        Role deletedRole = entityManager.find(Role.class, roleId);
        assertNull(deletedRole);
    }
}
