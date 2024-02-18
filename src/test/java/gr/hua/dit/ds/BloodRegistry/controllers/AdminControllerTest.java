package gr.hua.dit.ds.BloodRegistry.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Roles;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateUser() throws Exception {
        User mockUser = new User();

        when(userService.createUser(any(User.class), any(Roles.class))).thenReturn(mockUser);

        mockMvc.perform(post("/admin/create-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\",\"password\":\"password\",\"email\":\"test@example.com\"}")
                        .param("role", "ROLE_ADMIN"))
                .andExpect(status().isOk());

        verify(userService).createUser(any(User.class), any(Roles.class));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(any(Long.class));

        mockMvc.perform(delete("/admin/delete-user/{id}", 1L))
                .andExpect(status().isOk());

        verify(userService).deleteUser(any(Long.class));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateUserContactInfo() throws Exception {
        User mockUser = new User();
        // Configure mockUser as necessary

        when(userService.updateUser(any(Long.class), any(String.class), any(String.class))).thenReturn(mockUser);

        mockMvc.perform(put("/admin/update-user/{id}/contact-info", 1L)
                        .param("email", "newemail@example.com")
                        .param("username", "newUsername"))
                .andExpect(status().isOk());

        verify(userService).updateUser(any(Long.class), any(String.class), any(String.class));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateUserRole() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        when(userService.findUserById(any(Long.class))).thenReturn(user);
        doNothing().when(userService).updateUserRole(any(String.class), any(String.class));

        mockMvc.perform(put("/admin/update-user/{id}/role", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"ROLE_USER\""))
                .andExpect(status().isOk());

        verify(userService).findUserById(any(Long.class));
        verify(userService).updateUserRole(any(String.class), any(String.class));
    }
}
