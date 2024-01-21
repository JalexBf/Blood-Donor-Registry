package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.services.BloodDonorService;
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
public class BloodDonorControllerTest {

    @Mock
    private BloodDonorService bloodDonorService;

    @InjectMocks
    private BloodDonorController bloodDonorController;

    private BloodDonor bloodDonor;


    @BeforeEach
    void setUp() {
        bloodDonor = new BloodDonor();
        bloodDonor.setUserId(1L);
        // Initialize other necessary fields of BloodDonor
    }


    @Test
    void testCreateBloodDonor() {
        when(bloodDonorService.createBloodDonor(any(BloodDonor.class))).thenReturn(bloodDonor);
        ResponseEntity<BloodDonor> response = bloodDonorController.createBloodDonor(bloodDonor);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(bloodDonorService).createBloodDonor(any(BloodDonor.class));
    }


    @Test
    void testUpdateBloodDonor() {
        when(bloodDonorService.updateBloodDonor(any(BloodDonor.class))).thenReturn(bloodDonor);
        ResponseEntity<BloodDonor> response = bloodDonorController.updateBloodDonor(1L, bloodDonor);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(bloodDonorService).updateBloodDonor(any(BloodDonor.class));
    }


    @Test
    void testGetBloodDonor() {
        when(bloodDonorService.findBloodDonorById(anyLong())).thenReturn(Optional.of(bloodDonor));
        ResponseEntity<BloodDonor> response = bloodDonorController.getBloodDonor(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() != null);
        verify(bloodDonorService).findBloodDonorById(anyLong());
    }


    @Test
    void testGetBloodDonor_NotFound() {
        when(bloodDonorService.findBloodDonorById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<BloodDonor> response = bloodDonorController.getBloodDonor(1L);
        assertEquals(404, response.getStatusCodeValue());
        verify(bloodDonorService).findBloodDonorById(anyLong());
    }


    @Test
    void testGetAllBloodDonors() {
        when(bloodDonorService.findAllBloodDonors()).thenReturn(Arrays.asList(bloodDonor));
        ResponseEntity<Iterable<BloodDonor>> response = bloodDonorController.getAllBloodDonors();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(bloodDonorService).findAllBloodDonors();
    }


    @Test
    void testDeleteBloodDonor() {
        doNothing().when(bloodDonorService).deleteBloodDonor(anyLong());
        ResponseEntity<Void> response = bloodDonorController.deleteBloodDonor(1L);
        assertEquals(200, response.getStatusCodeValue());
        verify(bloodDonorService).deleteBloodDonor(anyLong());
    }
}

