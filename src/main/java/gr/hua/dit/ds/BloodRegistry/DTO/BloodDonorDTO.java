package gr.hua.dit.ds.BloodRegistry.DTO;


import java.time.LocalDate;
import java.util.Set;


public class BloodDonorDTO extends UserDTO {
    private String firstname;
    private String lastname;
    private String sex;
    private LocalDate birthdate;
    private String bloodtype;
    private long amka;
    private String region;
    private String phone;
    private String bloodworkFilePath;
    private LocalDate lastDonationDate;


    public BloodDonorDTO() {
        super();
    }


    public BloodDonorDTO(String username, String password, String email, Set<String> roles,
                         boolean accountNonExpired, boolean accountNonLocked,
                         boolean credentialsNonExpired, boolean enabled,
                         LocalDate passwordLastChangedDate, int failedLoginAttempts,
                         LocalDate accountLockDate, String firstname, String lastname,
                         String sex, LocalDate birthdate, String bloodtype, long amka,
                         String region, String phone, String bloodworkFilePath,
                         LocalDate lastDonationDate) {
        super();
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setRoles(roles);
        setAccountNonExpired(accountNonExpired);
        setAccountNonLocked(accountNonLocked);
        setCredentialsNonExpired(credentialsNonExpired);
        setEnabled(enabled);
        setPasswordLastChangedDate(passwordLastChangedDate);
        setFailedLoginAttempts(failedLoginAttempts);
        setAccountLockDate(accountLockDate);
        this.firstname = firstname;
        this.lastname = lastname;
        this.sex = sex;
        this.birthdate = birthdate;
        this.bloodtype = bloodtype;
        this.amka = amka;
        this.region = region;
        this.phone = phone;
        this.bloodworkFilePath = bloodworkFilePath;
        this.lastDonationDate = lastDonationDate;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getBloodtype() {
        return bloodtype;
    }

    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
    }

    public long getAmka() {
        return amka;
    }

    public void setAmka(long amka) {
        this.amka = amka;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBloodworkFilePath() {
        return bloodworkFilePath;
    }

    public void setBloodworkFilePath(String bloodworkFilePath) {
        this.bloodworkFilePath = bloodworkFilePath;
    }

    public LocalDate getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(LocalDate lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }
}
