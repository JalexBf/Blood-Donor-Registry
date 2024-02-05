package gr.hua.dit.ds.BloodRegistry.DTO;

import gr.hua.dit.ds.BloodRegistry.entities.enums.BloodType;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Roles;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Sex;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.aspectj.apache.bcel.generic.LOOKUPSWITCH;

import java.time.LocalDate;

@Data
public class UserDto {


    private Long userId;

    private Long secretariatId;

    private Long bloodDonorId;

    @NotEmpty
    @Size(min = 3, max = 15)
    private String username;

    @NotEmpty
    @Size(min = 8, max = 15)
    private String password;

    @Email
    @NotEmpty
    private String email;

    @NotNull
    private Long role; // ROLE_BLOOD_DONOR or ROLE_SECRETARIAT


    private String firstname;
    private String lastname;
    private Sex sex;
    private LocalDate birthdate;
    private BloodType bloodType;
    private long amka;
    private String region;
    private String phone;
    private LocalDate lastDonationDate;



}