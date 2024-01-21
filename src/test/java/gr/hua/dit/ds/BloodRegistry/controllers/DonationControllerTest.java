package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.entities.model.Donation;
import gr.hua.dit.ds.BloodRegistry.services.DonationService;
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
public class DonationControllerTest {

    @Mock
    private DonationService donationService;

    @InjectMocks
    private DonationController donationController;

    private Donation donation;

    @BeforeEach
    void setUp() {
        donation = new Donation();
        donation.setDonationId(1L);
        // Initialize other necessary fields of Donation
    }


    @Test
    void testCreateDonation() {
        when(donationService.createDonation(any(Donation.class))).thenReturn(donation);
        ResponseEntity<Donation> response = donationController.createDonation(donation);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(donationService).createDonation(any(Donation.class));
    }


    @Test
    void testUpdateDonation() {
        when(donationService.updateDonation(any(Donation.class))).thenReturn(donation);
        ResponseEntity<Donation> response = donationController.updateDonation(1L, donation);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(donationService).updateDonation(any(Donation.class));
    }


    @Test
    void testGetDonation() {
        when(donationService.findDonationById(anyLong())).thenReturn(Optional.of(donation));
        ResponseEntity<Donation> response = donationController.getDonation(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(donationService).findDonationById(anyLong());
    }


    @Test
    void testGetDonation_NotFound() {
        when(donationService.findDonationById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Donation> response = donationController.getDonation(1L);
        assertEquals(404, response.getStatusCodeValue());
        verify(donationService).findDonationById(anyLong());
    }


    @Test
    void testGetAllDonations() {
        when(donationService.findAllDonations()).thenReturn(Arrays.asList(donation));
        ResponseEntity<Iterable<Donation>> response = donationController.getAllDonations();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(donationService).findAllDonations();
    }


    @Test
    void testDeleteDonation() {
        doNothing().when(donationService).deleteDonation(anyLong());
        ResponseEntity<Void> response = donationController.deleteDonation(1L);
        assertEquals(200, response.getStatusCodeValue());
        verify(donationService).deleteDonation(anyLong());
    }
}
