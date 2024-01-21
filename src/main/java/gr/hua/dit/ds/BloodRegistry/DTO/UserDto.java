package gr.hua.dit.ds.BloodRegistry.DTO;

public class UserDto {
    private String username;
    private String email;
    private String password;
    private String role; // To distinguish between blood donor and secretariat
    // Fields specific to blood donors
    private String firstname;
    private String lastname;
    private String region;
    private String phone;
}
