package gr.hua.dit.ds.BloodRegistry.entities.Interfaces;

import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;

import java.time.LocalDate;

public interface IDonation {

    Long getDonationId();
    LocalDate getDonationDate();
    BloodDonor getBloodDonor();

    void setDonationId(Long id);
    void setDonationDate(LocalDate donationDate);
    void setBloodDonor(BloodDonor bloodDonor);
}
