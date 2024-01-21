package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.RegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;


public class RegistrationServiceTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createRegistration() {
        Registration registration = new Registration();

        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        Registration created = registrationService.createRegistration(registration);

        assertThat(created).isNotNull();
        verify(registrationRepository, times(1)).save(any(Registration.class));
    }


    @Test
    void approveRegistration() {
        Registration registration = new Registration();
        registration.setStatus(Status.AWAITING);

        when(registrationRepository.findById(anyLong())).thenReturn(Optional.of(registration));
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        Registration updated = registrationService.approveRegistration(1L);

        assertThat(updated.getStatus()).isEqualTo(Status.APPROVED);
        verify(registrationRepository, times(1)).save(registration);
    }


    @Test
    void rejectRegistration() {
        Registration registration = new Registration();
        registration.setStatus(Status.AWAITING);

        when(registrationRepository.findById(anyLong())).thenReturn(Optional.of(registration));
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        Registration updated = registrationService.rejectRegistration(1L);

        assertThat(updated.getStatus()).isEqualTo(Status.REJECTED);
        verify(registrationRepository, times(1)).save(registration);
    }


    @Test
    void findRegistrationById_WhenRegistrationExists() {
        Registration registration = new Registration();

        when(registrationRepository.findById(anyLong())).thenReturn(Optional.of(registration));

        Registration found = registrationService.findRegistrationById(1L);

        assertThat(found).isNotNull();
        verify(registrationRepository, times(1)).findById(anyLong());
    }


    @Test
    void findRegistrationById_NotExist() {
        when(registrationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> registrationService.findRegistrationById(1L));
        verify(registrationRepository, times(1)).findById(anyLong());
    }


    @Test
    void findAllRegistrations() {
        List<Registration> registrations = Arrays.asList(new Registration(), new Registration());

        when(registrationRepository.findAll()).thenReturn(registrations);

        List<Registration> foundRegistrations = registrationService.findAllRegistrations();

        assertThat(foundRegistrations).hasSize(2);
        verify(registrationRepository, times(1)).findAll();
    }


    @Test
    void findAllRegistrationsByStatus() {
        Status testStatus = Status.AWAITING;

        Registration reg1 = new Registration();
        reg1.setStatus(testStatus);

        Registration reg2 = new Registration();
        reg2.setStatus(testStatus);

        List<Registration> awaitingRegistrations = Arrays.asList(reg1, reg2);

        when(registrationRepository.findByStatus(testStatus)).thenReturn(awaitingRegistrations);

        List<Registration> foundRegistrations = registrationService.findAllRegistrationsByStatus(testStatus);

        assertThat(foundRegistrations).hasSize(2);
        assertThat(foundRegistrations).allMatch(registration -> registration.getStatus() == testStatus);
        verify(registrationRepository, times(1)).findByStatus(testStatus);
    }

}
