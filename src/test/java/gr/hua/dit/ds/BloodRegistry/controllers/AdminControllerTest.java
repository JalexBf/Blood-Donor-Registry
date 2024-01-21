package gr.hua.dit.ds.BloodRegistry.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


public class AdminControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        objectMapper = new ObjectMapper();
    }


    @Test
    public void testDeleteUser() throws Exception {
        Long userId = 1L;

        doNothing().when(userService).deleteUser(userId);

        mockMvc.perform(delete("/admin/delete-user/{id}", userId))
                .andExpect(status().isOk());

        verify(userService).deleteUser(userId);
    }


    @Test
    public void testUpdateUser() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        // Set other necessary fields

        // Updating the mock setup to match the new method signature with isAdmin parameter
        when(userService.updateUser(any(User.class), eq(true))).thenReturn(user);

        mockMvc.perform(put("/admin/update-user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        // Verify that the updated method is called with the isAdmin parameter
        verify(userService).updateUser(any(User.class), eq(true));
    }
}
