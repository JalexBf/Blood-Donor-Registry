package gr.hua.dit.ds.BloodRegistry.services;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Donation;
import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import gr.hua.dit.ds.BloodRegistry.exceptions.InvalidRequestException;
import gr.hua.dit.ds.BloodRegistry.repositories.BloodDonorRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.DonationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class DonationServiceTest {

    @Mock
    private DonationRepository donationRepository;

    @Mock
    private BloodDonorRepository bloodDonorRepository;

    @InjectMocks
    private DonationService donationService;

    private Donation donation;
    private BloodDonor bloodDonor;
    private Registration registration; // Declare Registration


    @BeforeEach
    void setUp() {
        registration = new Registration();
        registration.setStatus(Status.APPROVED);

        bloodDonor = new BloodDonor();
        bloodDonor.setUserId(1L);
        bloodDonor.setRegistration(registration);

        donation = new Donation();
        donation.setDonationId(1L);
        donation.setBloodDonor(bloodDonor);
    }


    @Test
    void testCreateDonation_Success() {
        Registration testRegistration = new Registration();
        testRegistration.setStatus(Status.APPROVED);
        bloodDonor.setRegistration(testRegistration);

        when(bloodDonorRepository.save(any(BloodDonor.class))).thenReturn(bloodDonor);
        when(donationRepository.save(any(Donation.class))).thenReturn(donation);

        Donation createdDonation = donationService.createDonation(donation);

        assertNotNull(createdDonation);
        assertEquals(LocalDate.now(), createdDonation.getBloodDonor().getLastDonationDate());
        verify(donationRepository).save(donation);
        verify(bloodDonorRepository).save(bloodDonor);
    }


    @Test
    void testCreateDonation_InvalidRequest() {
        registration.setStatus(Status.AWAITING);

        assertThrows(InvalidRequestException.class, () -> donationService.createDonation(donation));
        verify(donationRepository, never()).save(any(Donation.class));
    }


    @Test
    void testUpdateDonation() {
        when(donationRepository.save(any(Donation.class))).thenReturn(donation);

        Donation updatedDonation = donationService.updateDonation(donation);

        assertNotNull(updatedDonation);
        verify(donationRepository).save(donation);
    }


    @Test
    void testFindDonationById() {
        when(donationRepository.findById(anyLong())).thenReturn(Optional.of(donation));

        Optional<Donation> foundDonation = donationService.findDonationById(1L);

        assertTrue(foundDonation.isPresent());
        assertEquals(donation.getDonationId(), foundDonation.get().getDonationId());
        verify(donationRepository).findById(1L);
    }


    @Test
    void testFindAllDonations() {
        when(donationRepository.findAll()).thenReturn(Arrays.asList(donation));

        List<Donation> donations = donationService.findAllDonations();

        assertFalse(donations.isEmpty());
        verify(donationRepository).findAll();
    }


    @Test
    void testDeleteDonation() {
        doNothing().when(donationRepository).deleteById(anyLong());

        donationService.deleteDonation(1L);

        verify(donationRepository).deleteById(1L);
    }
}
