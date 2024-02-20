package gr.hua.dit.ds.BloodRegistry.entities.Interfaces;

import gr.hua.dit.ds.BloodRegistry.entities.model.Application;

import java.util.List;

public interface ISecretariat extends IUser {

    String getFirstname();
    String getLastname();
    List<Application> getapplications();

    void setFirstname(String firstname);
    void setLastname(String lastname);
    void setapplications(List<Application> applications);
}
