package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.model.Secretariat;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.SecretariatRepository;
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
public class SecretariatServiceTest {

    @Mock
    private SecretariatRepository secretariatRepository;

    @InjectMocks
    private SecretariatService SecretariatService;

    private Secretariat Secretariat;

    @BeforeEach
    public void setup() {
        Secretariat = new Secretariat();
        Secretariat.setUserId(1L);
    }


    @Test
    public void testCreateSecretariat() {
        when(secretariatRepository.save(any(Secretariat.class))).thenReturn(Secretariat);

        Secretariat createdSecretariat = SecretariatService.createSecretariat(Secretariat);

        assertNotNull(createdSecretariat);
        assertEquals(Secretariat.getSecretariatId(), createdSecretariat.getSecretariatId());
        verify(secretariatRepository).save(Secretariat);
    }


    @Test
    public void testUpdateSecretariat() {
        when(secretariatRepository.findById(Secretariat.getSecretariatId())).thenReturn(Optional.of(Secretariat));
        when(secretariatRepository.save(any(Secretariat.class))).thenReturn(Secretariat);

        Secretariat updatedSecretariat = SecretariatService.updateSecretariat(Secretariat);

        assertNotNull(updatedSecretariat);
        verify(secretariatRepository).findById(Secretariat.getSecretariatId());
        verify(secretariatRepository).save(Secretariat);
    }


    @Test
    public void testUpdateSecretariat_NotFound() {
        when(secretariatRepository.findById(Secretariat.getSecretariatId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> SecretariatService.updateSecretariat(Secretariat));
    }


    @Test
    public void testFindSecretariatById() {
        when(secretariatRepository.findById(Secretariat.getSecretariatId())).thenReturn(Optional.of(Secretariat));

        Optional<Secretariat> foundSecretariat = SecretariatService.findSecretariatById(Secretariat.getSecretariatId());

        assertTrue(foundSecretariat.isPresent());
        assertEquals(Secretariat.getSecretariatId(), foundSecretariat.get().getSecretariatId());
        verify(secretariatRepository).findById(Secretariat.getSecretariatId());
    }


    @Test
    public void testFindAllSecretariats() {
        when(secretariatRepository.findAll()).thenReturn(Arrays.asList(Secretariat));

        List<Secretariat> Secretariats = SecretariatService.findAllSecretariats();

        assertFalse(Secretariats.isEmpty());
        verify(secretariatRepository).findAll();
    }


    @Test
    public void testDeleteSecretariat() {
        when(secretariatRepository.findById(Secretariat.getSecretariatId())).thenReturn(Optional.of(Secretariat));
        doNothing().when(secretariatRepository).deleteById(Secretariat.getSecretariatId());

        SecretariatService.deleteSecretariat(Secretariat.getSecretariatId());

        verify(secretariatRepository).findById(Secretariat.getSecretariatId());
        verify(secretariatRepository).deleteById(Secretariat.getSecretariatId());
    }


    @Test
    public void testDeleteSecretariat_NotFound() {
        when(secretariatRepository.findById(Secretariat.getSecretariatId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> SecretariatService.deleteSecretariat(Secretariat.getSecretariatId()));
    }
}

