package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.services.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }


    @Test
    public void testUpdateRole() throws Exception {
        Role role = new Role();
        role.setRoleId(1L);
        when(roleService.updateRole(any(Role.class))).thenReturn(role);

        mockMvc.perform(put("/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"ROLE_USER\" }")) // Example request body
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleId").value(1L));

        verify(roleService).updateRole(any(Role.class));
    }


    @Test
    public void testUpdateRoleNotFound() throws Exception {
        when(roleService.updateRole(any(Role.class))).thenThrow(new NotFoundException("Role not found"));

        mockMvc.perform(put("/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"ROLE_USER\" }"))
                .andExpect(status().isNotFound());

        verify(roleService).updateRole(any(Role.class));
    }


    @Test
    public void testGetRole() throws Exception {
        Role role = new Role();
        role.setRoleId(1L);
        when(roleService.findRoleById(1L)).thenReturn(Optional.of(role));

        mockMvc.perform(get("/roles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleId").value(1L));

        verify(roleService).findRoleById(1L);
    }


    @Test
    public void testGetRoleNotFound() throws Exception {
        when(roleService.findRoleById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/roles/1"))
                .andExpect(status().isNotFound());

        verify(roleService).findRoleById(anyLong());
    }


    @Test
    public void testGetAllRoles() throws Exception {
        when(roleService.findAllRoles()).thenReturn(Arrays.asList(new Role(), new Role()));

        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(roleService).findAllRoles();
    }

}
