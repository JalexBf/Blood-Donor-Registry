package gr.hua.dit.ds.BloodRegistry.entities.Interfaces;

import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;

import java.util.List;

public interface ISecreteriat extends IUser {

    String getFirstname();
    String getLastname();
    List<Registration> getRegistrations();

    void setFirstname(String firstname);
    void setLastname(String lastname);
    void setRegistrations(List<Registration> registrations);
}
