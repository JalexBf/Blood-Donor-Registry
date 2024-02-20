package gr.hua.dit.ds.BloodRegistry.entities;


import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Permissions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class RoleTest {

    private Role role;
    private Validator validator;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setRoleId(1L);
        role.setName("ROLE_ADMIN");

        Set<Permissions> permissions = new HashSet<>();
        permissions.add(Permissions.READ_USER);
        permissions.add(Permissions.WRITE_USER);
        role.setPermissions(permissions);

        // Validator setup
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    void testRoleGettersAndSetters() {
        assertEquals(1L, role.getRoleId());
        assertEquals("ROLE_ADMIN", role.getName());
        assertEquals(2, role.getPermissions().size());
        assertTrue(role.getPermissions().contains(Permissions.READ_USER));
        assertTrue(role.getPermissions().contains(Permissions.WRITE_USER));
    }


    @Test
    void testRoleWithEmptyName() {
        role.setName("");
        Set<ConstraintViolation<Role>> violations = validator.validateProperty(role, "name");
        assertFalse(violations.isEmpty(), "Empty role name should result validation error");
    }


    @Test
    void testRoleWithNullPermissions() {
        role.setPermissions(null);
        Set<ConstraintViolation<Role>> violations = validator.validateProperty(role, "permissions");
        assertFalse(violations.isEmpty(), "Null permissions should result validation error");
    }


    @Test
    void testValidRole() {
        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertTrue(violations.isEmpty(), "No violations should be present valid role");
    }
}