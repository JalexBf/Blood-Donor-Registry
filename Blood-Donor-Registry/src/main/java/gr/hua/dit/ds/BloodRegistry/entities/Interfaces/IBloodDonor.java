package gr.hua.dit.ds.BloodRegistry.entities.Interfaces;

import gr.hua.dit.ds.BloodRegistry.entities.enums.BloodType;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Sex;
import gr.hua.dit.ds.BloodRegistry.entities.model.Donation;
import gr.hua.dit.ds.BloodRegistry.entities.model.Registration;
import java.time.LocalDate;
import java.util.List;


public interface IBloodDonor {

    // Getters
    String getFirstname();
    String getLastname();
    Sex getSex();
    LocalDate getBirthdate();
    BloodType getBloodType();
    long getAmka();
    String getRegion();
    String getPhone();
    String getBloodworkFilePath();
    LocalDate getLastDonationDate();
    List<Donation> getDonations();
    Registration getRegistration();

    // Setters
    void setFirstname(String firstname);
    void setLastname(String lastname);
    void setSex(Sex sex);
    void setBirthdate(LocalDate birthdate);
    void setBloodType(BloodType bloodtype);
    void setAmka(long amka);
    void setRegion(String region);
    void setPhone(String phone);
    void setBloodworkFilePath(String bloodworkFilePath);
    void setLastDonationDate(LocalDate lastDonationDate);
    void setDonations(List<Donation> donations);
    void setRegistration(Registration registration);
}
