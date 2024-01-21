package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.entities.model.Secreteriat;
import gr.hua.dit.ds.BloodRegistry.services.SecreteriatService;
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
public class SecreteriatControllerTest {

    @Mock
    private SecreteriatService secreteriatService;

    @InjectMocks
    private SecreteriatController secreteriatController;

    private Secreteriat secreteriat;

    @BeforeEach
    void setUp() {
        secreteriat = new Secreteriat();
        secreteriat.setUserId(1L);
        // Initialize other necessary fields of Secreteriat
    }

    @Test
    void testCreateSecreteriat() {
        when(secreteriatService.createSecreteriat(any(Secreteriat.class))).thenReturn(secreteriat);
        ResponseEntity<Secreteriat> response = secreteriatController.createSecreteriat(secreteriat);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(secreteriatService).createSecreteriat(any(Secreteriat.class));
    }

    @Test
    void testUpdateSecreteriat() {
        when(secreteriatService.updateSecreteriat(any(Secreteriat.class))).thenReturn(secreteriat);
        ResponseEntity<Secreteriat> response = secreteriatController.updateSecreteriat(1L, secreteriat);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(secreteriatService).updateSecreteriat(any(Secreteriat.class));
    }

    @Test
    void testGetSecreteriat() {
        when(secreteriatService.findSecreteriatById(anyLong())).thenReturn(Optional.of(secreteriat));
        ResponseEntity<Secreteriat> response = secreteriatController.getSecreteriat(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() != null);
        verify(secreteriatService).findSecreteriatById(anyLong());
    }

    @Test
    void testGetSecreteriat_NotFound() {
        when(secreteriatService.findSecreteriatById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Secreteriat> response = secreteriatController.getSecreteriat(1L);
        assertEquals(404, response.getStatusCodeValue());
        verify(secreteriatService).findSecreteriatById(anyLong());
    }

    @Test
    void testGetAllSecreteriats() {
        when(secreteriatService.findAllSecreteriats()).thenReturn(Arrays.asList(secreteriat));
        ResponseEntity<Iterable<Secreteriat>> response = secreteriatController.getAllSecreteriats();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(secreteriatService).findAllSecreteriats();
    }

    @Test
    void testDeleteSecreteriat() {
        doNothing().when(secreteriatService).deleteSecreteriat(anyLong());
        ResponseEntity<Void> response = secreteriatController.deleteSecreteriat(1L);
        assertEquals(200, response.getStatusCodeValue());
        verify(secreteriatService).deleteSecreteriat(anyLong());
    }
}
