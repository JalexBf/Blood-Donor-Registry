package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.services.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {

    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private RegistrationController registrationController;

    private Registration registration;

    @BeforeEach
    void setUp() {
        registration = new Registration();
    }


    @Test
    void testCreateRegistration() {
        when(registrationService.createRegistration(any(Registration.class))).thenReturn(registration);
        ResponseEntity<Registration> response = registrationController.createRegistration(registration);
        assertEquals(response.getStatusCodeValue(), 200);
        assertNotNull(response.getBody());
        verify(registrationService).createRegistration(any(Registration.class));
    }


    @Test
    void testGetRegistration_Success() {
        when(registrationService.findRegistrationById(anyLong())).thenReturn(registration);
        ResponseEntity<Registration> response = registrationController.getRegistration(1L);
        assertEquals(response.getStatusCodeValue(), 200);
        assertNotNull(response.getBody());
        verify(registrationService).findRegistrationById(anyLong());
    }


    @Test
    void testGetRegistration_NotFound() {
        when(registrationService.findRegistrationById(anyLong())).thenThrow(new NotFoundException("Not found"));
        ResponseEntity<Registration> response = registrationController.getRegistration(1L);
        assertEquals(response.getStatusCodeValue(), 404);
        verify(registrationService).findRegistrationById(anyLong());
    }


    @Test
    void testGetAllRegistrations() {
        when(registrationService.findAllRegistrations()).thenReturn(Arrays.asList(registration));
        ResponseEntity<Iterable<Registration>> response = registrationController.getAllRegistrations();
        assertEquals(response.getStatusCodeValue(), 200);
        assertNotNull(response.getBody());
        verify(registrationService).findAllRegistrations();
    }


    @Test
    void testDeleteRegistration() {
        doNothing().when(registrationService).deleteRegistration(anyLong());
        ResponseEntity<Void> response = registrationController.deleteRegistration(1L);
        assertEquals(response.getStatusCodeValue(), 200);
        verify(registrationService).deleteRegistration(anyLong());
    }
}

