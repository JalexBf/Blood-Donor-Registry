package gr.hua.dit.ds.BloodRegistry.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    @Test
    public void testGetUser() throws Exception {
        User mockUser = new User(); // Assuming User class has properties to set
        mockUser.setUserId(1L);
        when(userService.findUserById(1L)).thenReturn(mockUser);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L)); // Updated to match the field name


        verify(userService).findUserById(1L);
    }


    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User()); // Add mock users as needed
        users.add(new User());

        when(userService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(users.size())));

        verify(userService).findAllUsers();
    }


    @Test
    public void testDeleteUser() throws Exception {
        Long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        mockMvc.perform(delete("/users/" + userId))
                .andExpect(status().isOk());

        verify(userService).deleteUser(userId);
    }

}
