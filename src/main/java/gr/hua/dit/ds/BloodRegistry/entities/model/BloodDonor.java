package gr.hua.dit.ds.BloodRegistry.entities.model;

import gr.hua.dit.ds.BloodRegistry.entities.enums.BloodType;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Sex;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper=false)
public class BloodDonor extends User {

    @Column
    @NotEmpty
    @Size(min = 2, max = 50)
    private String firstname;

    @Column
    @NotEmpty
    @Size(min = 2, max = 50)
    private String lastname;

    @Column
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column
    private LocalDate birthdate;

    @Column
    @Enumerated(EnumType.STRING)
    private BloodType bloodtype;

    @Column
    private long amka;

    @Column
    @NotEmpty
    @Size(min = 2, max = 50)
    private String region;

    @Column
    @NotEmpty
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phone;

    @Column
    private String bloodworkFilePath;

    @OneToMany(mappedBy = "bloodDonor")
    private List<Donation> donations;

    @OneToOne(mappedBy = "bloodDonor", cascade = CascadeType.ALL)
    private Registration registration;

}
