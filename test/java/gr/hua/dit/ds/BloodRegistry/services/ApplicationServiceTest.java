package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.Application;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.repositories.ApplicationRepository;
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


public class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ApplicationService applicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createapplication() {
        Application application = new Application();

        when(applicationRepository.save(any(Application.class))).thenReturn(application);

        Application created = applicationService.createApplication(application);

        assertThat(created).isNotNull();
        verify(applicationRepository, times(1)).save(any(Application.class));
    }


    @Test
    void approveapplication_UpdatesUserRole() {
        Long applicationId = 1L;
        Application application = new Application();
        application.setStatus(Status.AWAITING);
        // Assuming you have a method in Application to get the username of the applicant
        String applicantUsername = "username";
        application.setBloodDonor(new BloodDonor());
        application.getBloodDonor().setUsername(applicantUsername);

        when(applicationRepository.findById(applicationId)).thenReturn(Optional.of(application));
        when(applicationRepository.save(any(Application.class))).thenReturn(application);
        doNothing().when(userService).updateUserRole(applicantUsername, "BLOOD_DONOR");

        Application updated = applicationService.approveapplication(applicationId);

        assertThat(updated.getStatus()).isEqualTo(Status.APPROVED);
        verify(applicationRepository, times(1)).save(application);
        verify(userService, times(1)).updateUserRole(applicantUsername, "BLOOD_DONOR");
    }



    @Test
    void rejectapplication() {
        Application application = new Application();
        application.setStatus(Status.AWAITING);

        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(application));
        when(applicationRepository.save(any(Application.class))).thenReturn(application);

        Application updated = applicationService.rejectapplication(1L);

        assertThat(updated.getStatus()).isEqualTo(Status.REJECTED);
        verify(applicationRepository, times(1)).save(application);
    }


    @Test
    void findapplicationById_WhenapplicationExists() {
        Application application = new Application();

        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(application));

        Application found = applicationService.findApplicationById(1L);

        assertThat(found).isNotNull();
        verify(applicationRepository, times(1)).findById(anyLong());
    }


    @Test
    void findapplicationById_NotExist() {
        when(applicationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> applicationService.findApplicationById(1L));
        verify(applicationRepository, times(1)).findById(anyLong());
    }


    @Test
    void findAllapplications() {
        List<Application> applications = Arrays.asList(new Application(), new Application());

        when(applicationRepository.findAll()).thenReturn(applications);

        List<Application> foundApplications = applicationService.findAllApplications();

        assertThat(foundApplications).hasSize(2);
        verify(applicationRepository, times(1)).findAll();
    }


    @Test
    void findAllapplicationsByStatus() {
        Status testStatus = Status.AWAITING;

        Application reg1 = new Application();
        reg1.setStatus(testStatus);

        Application reg2 = new Application();
        reg2.setStatus(testStatus);

        List<Application> awaitingApplications = Arrays.asList(reg1, reg2);

        when(applicationRepository.findByStatus(testStatus)).thenReturn(awaitingApplications);

        List<Application> foundApplications = applicationService.findAllApplicationsByStatus(testStatus);

        assertThat(foundApplications).hasSize(2);
        assertThat(foundApplications).allMatch(application -> application.getStatus() == testStatus);
        verify(applicationRepository, times(1)).findByStatus(testStatus);
    }

}
