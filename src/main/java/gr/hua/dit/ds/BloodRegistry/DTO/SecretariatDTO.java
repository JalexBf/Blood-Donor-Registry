package gr.hua.dit.ds.BloodRegistry.DTO;

public class SecretariatDTO extends UserDTO {
    private String firstname;
    private String lastname;

    public SecretariatDTO() {
        super();
    }


    public SecretariatDTO(String username, String email, String firstname, String lastname) {
        setUsername(username);
        setEmail(email);
        this.firstname = firstname;
        this.lastname = lastname;
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
}

