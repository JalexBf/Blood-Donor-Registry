package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.entities.model.Application;
import gr.hua.dit.ds.BloodRegistry.exceptions.NotFoundException;
import gr.hua.dit.ds.BloodRegistry.services.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationControllerTest {

    @Mock
    private ApplicationService applicationService;

    @InjectMocks
    private ApplicationController applicationController;

    private Application application;

    @BeforeEach
    void setUp() {
        application = new Application();
    }


    @Test
    void testCreateapplication() {
        when(applicationService.createApplication(any(Application.class))).thenReturn(application);
        ResponseEntity<Application> response = applicationController.createapplication(application);
        assertEquals(response.getStatusCodeValue(), 200);
        assertNotNull(response.getBody());
        verify(applicationService).createApplication(any(Application.class));
    }


    @Test
    void testGetapplication_Success() {
        when(applicationService.findApplicationById(anyLong())).thenReturn(application);
        ResponseEntity<Application> response = applicationController.getapplication(1L);
        assertEquals(response.getStatusCodeValue(), 200);
        assertNotNull(response.getBody());
        verify(applicationService).findApplicationById(anyLong());
    }


    @Test
    void testGetapplication_NotFound() {
        when(applicationService.findApplicationById(anyLong())).thenThrow(new NotFoundException("Not found"));
        ResponseEntity<Application> response = applicationController.getapplication(1L);
        assertEquals(response.getStatusCodeValue(), 404);
        verify(applicationService).findApplicationById(anyLong());
    }


    @Test
    void testGetAllapplications() {
        when(applicationService.findAllApplications()).thenReturn(Arrays.asList(application));
        ResponseEntity<Iterable<Application>> response = applicationController.getAllapplications();
        assertEquals(response.getStatusCodeValue(), 200);
        assertNotNull(response.getBody());
        verify(applicationService).findAllApplications();
    }


    @Test
    void testDeleteapplication() {
        doNothing().when(applicationService).deleteApplication(anyLong());
        ResponseEntity<Void> response = applicationController.deleteapplication(1L);
        assertEquals(response.getStatusCodeValue(), 200);
        verify(applicationService).deleteApplication(anyLong());
    }
}

