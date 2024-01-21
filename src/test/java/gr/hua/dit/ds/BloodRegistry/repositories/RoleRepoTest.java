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


    /**
     * Test creation and retrieval of a role by an admin.
     * Ensure that a newly created role is persisted correctly and can be retrieved.
     */
    @Test
    public void adminCreatesAndFindsRole() {
        // New admin
        Role adminCreatedRole = new Role();
        adminCreatedRole.setName("SECRETARIAT_ROLE");
        adminCreatedRole.setPermissions(EnumSet.of(Permissions.READ_USER, Permissions.WRITE_USER));

        // Persist and retrieverole
        adminCreatedRole = entityManager.persistFlushFind(adminCreatedRole);
        Role foundRole = entityManager.find(Role.class, adminCreatedRole.getRoleId());

        assertNotNull(foundRole);
        assertEquals("SECRETARIAT_ROLE", foundRole.getName());
        assertTrue(foundRole.getPermissions().containsAll(EnumSet.of(Permissions.READ_USER, Permissions.WRITE_USER)));
    }


    /**
     * Test update of a role by an admin.
     * Verify that changes made to a role are persisted and retrieved correctly.
     */
    @Test
    public void adminUpdatesRole() {
        Role roleToBeUpdated = new Role();
        roleToBeUpdated.setName("BLOOD_DONOR_ROLE");
        roleToBeUpdated.setPermissions(EnumSet.of(Permissions.DELETE_USER));

        roleToBeUpdated = entityManager.persistFlushFind(roleToBeUpdated);

        // Admin updating the role
        roleToBeUpdated.setName("BLOOD_DONOR_ROLE");
        roleToBeUpdated.setPermissions(EnumSet.of(Permissions.READ_USER));

        Role updatedRole = entityManager.persistFlushFind(roleToBeUpdated);

        assertEquals("BLOOD_DONOR_ROLE", updatedRole.getName());
        assertTrue(updatedRole.getPermissions().contains(Permissions.READ_USER));
        assertFalse(updatedRole.getPermissions().contains(Permissions.DELETE_USER));
    }


    /**
     * Test the deletion of a role by an admin.
     * Ensures that a deleted role is removed from persistence.
     */
    @Test
    public void adminDeletesRole() {
        Role roleToDelete = new Role();
        roleToDelete.setName("BLOOD_DONOR_ROLE");
        roleToDelete.setPermissions(EnumSet.of(Permissions.DELETE_USER));

        roleToDelete = entityManager.persistFlushFind(roleToDelete);

        // Admin deleting role
        entityManager.remove(roleToDelete);
        entityManager.flush();

        Role deletedRole = entityManager.find(Role.class, roleToDelete.getRoleId());
        assertNull(deletedRole);
    }
}
