package gr.hua.dit.ds.BloodRegistry.entities.Interfaces;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secreteriat;

import java.time.LocalDate;

public interface IRegistration {

    Long getRegistrationId();
    Status getStatus();
    LocalDate getSubmissionDate();
    BloodDonor getBloodDonor();
    Secreteriat getSecreteriat();

    void setRegistrationId(Long id);
    void setStatus(Status status);
    void setSubmissionDate(LocalDate submissionDate);
    void setBloodDonor(BloodDonor bloodDonor);
    void setSecreteriat(Secreteriat secreteriat);
}
