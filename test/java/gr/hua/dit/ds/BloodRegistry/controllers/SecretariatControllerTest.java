package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.entities.model.Secretariat;
import gr.hua.dit.ds.BloodRegistry.services.SecretariatService;
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
public class SecretariatControllerTest {

    @Mock
    private SecretariatService SecretariatService;

    @InjectMocks
    private SecretariatController secretariatController;

    private Secretariat Secretariat;

    @BeforeEach
    void setUp() {
        Secretariat = new Secretariat();
        Secretariat.setUserId(1L);
        // Initialize other necessary fields of Secretariat
    }


    @Test
    void testCreateSecretariat() {
        when(SecretariatService.createSecretariat(any(Secretariat.class))).thenReturn(Secretariat);
        ResponseEntity<Secretariat> response = secretariatController.createSecretariat(Secretariat);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(SecretariatService).createSecretariat(any(Secretariat.class));
    }


    @Test
    void testUpdateSecretariat() {
        when(SecretariatService.updateSecretariat(any(Secretariat.class))).thenReturn(Secretariat);
        ResponseEntity<Secretariat> response = secretariatController.updateSecretariat(1L, Secretariat);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(SecretariatService).updateSecretariat(any(Secretariat.class));
    }


    @Test
    void testGetSecretariat() {
        when(SecretariatService.findSecretariatById(anyLong())).thenReturn(Optional.of(Secretariat));
        ResponseEntity<Secretariat> response = secretariatController.getSecretariat(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() != null);
        verify(SecretariatService).findSecretariatById(anyLong());
    }

    @Test
    void testGetSecretariat_NotFound() {
        when(SecretariatService.findSecretariatById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Secretariat> response = secretariatController.getSecretariat(1L);
        assertEquals(404, response.getStatusCodeValue());
        verify(SecretariatService).findSecretariatById(anyLong());
    }


    @Test
    void testGetAllSecretariats() {
        when(SecretariatService.findAllSecretariats()).thenReturn(Arrays.asList(Secretariat));
        ResponseEntity<Iterable<Secretariat>> response = secretariatController.getAllSecretariats();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(SecretariatService).findAllSecretariats();
    }


    @Test
    void testDeleteSecretariat() {
        doNothing().when(SecretariatService).deleteSecretariat(anyLong());
        ResponseEntity<Void> response = secretariatController.deleteSecretariat(1L);
        assertEquals(200, response.getStatusCodeValue());
        verify(SecretariatService).deleteSecretariat(anyLong());
    }
}
