package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.model.Secreteriat;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.SecreteriatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class SecreteriatServiceTest {

    @Mock
    private SecreteriatRepository secreteriatRepository;

    @InjectMocks
    private SecreteriatService secreteriatService;

    private Secreteriat secreteriat;

    @BeforeEach
    public void setup() {
        secreteriat = new Secreteriat();
        secreteriat.setUserId(1L);
    }


    @Test
    public void testCreateSecreteriat() {
        when(secreteriatRepository.save(any(Secreteriat.class))).thenReturn(secreteriat);

        Secreteriat createdSecreteriat = secreteriatService.createSecreteriat(secreteriat);

        assertNotNull(createdSecreteriat);
        assertEquals(secreteriat.getSecreteriatId(), createdSecreteriat.getSecreteriatId());
        verify(secreteriatRepository).save(secreteriat);
    }


    @Test
    public void testUpdateSecreteriat() {
        when(secreteriatRepository.findById(secreteriat.getSecreteriatId())).thenReturn(Optional.of(secreteriat));
        when(secreteriatRepository.save(any(Secreteriat.class))).thenReturn(secreteriat);

        Secreteriat updatedSecreteriat = secreteriatService.updateSecreteriat(secreteriat);

        assertNotNull(updatedSecreteriat);
        verify(secreteriatRepository).findById(secreteriat.getSecreteriatId());
        verify(secreteriatRepository).save(secreteriat);
    }


    @Test
    public void testUpdateSecreteriat_NotFound() {
        when(secreteriatRepository.findById(secreteriat.getSecreteriatId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> secreteriatService.updateSecreteriat(secreteriat));
    }


    @Test
    public void testFindSecreteriatById() {
        when(secreteriatRepository.findById(secreteriat.getSecreteriatId())).thenReturn(Optional.of(secreteriat));

        Optional<Secreteriat> foundSecreteriat = secreteriatService.findSecreteriatById(secreteriat.getSecreteriatId());

        assertTrue(foundSecreteriat.isPresent());
        assertEquals(secreteriat.getSecreteriatId(), foundSecreteriat.get().getSecreteriatId());
        verify(secreteriatRepository).findById(secreteriat.getSecreteriatId());
    }


    @Test
    public void testFindAllSecreteriats() {
        when(secreteriatRepository.findAll()).thenReturn(Arrays.asList(secreteriat));

        List<Secreteriat> secreteriats = secreteriatService.findAllSecreteriats();

        assertFalse(secreteriats.isEmpty());
        verify(secreteriatRepository).findAll();
    }


    @Test
    public void testDeleteSecreteriat() {
        when(secreteriatRepository.findById(secreteriat.getSecreteriatId())).thenReturn(Optional.of(secreteriat));
        doNothing().when(secreteriatRepository).deleteById(secreteriat.getSecreteriatId());

        secreteriatService.deleteSecreteriat(secreteriat.getSecreteriatId());

        verify(secreteriatRepository).findById(secreteriat.getSecreteriatId());
        verify(secreteriatRepository).deleteById(secreteriat.getSecreteriatId());
    }


    @Test
    public void testDeleteSecreteriat_NotFound() {
        when(secreteriatRepository.findById(secreteriat.getSecreteriatId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> secreteriatService.deleteSecreteriat(secreteriat.getSecreteriatId()));
    }
}

