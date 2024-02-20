package gr.hua.dit.ds.BloodRegistry.entities.Interfaces;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secretariat;

import java.time.LocalDate;

public interface IApplication {

    Long getapplicationId();
    Status getStatus();
    LocalDate getSubmissionDate();
    BloodDonor getBloodDonor();
    Secretariat getSecretariat();

    void setapplicationId(Long id);
    void setStatus(Status status);
    void setSubmissionDate(LocalDate submissionDate);
    void setBloodDonor(BloodDonor bloodDonor);
    void setSecretariat(Secretariat secretariat);
}
