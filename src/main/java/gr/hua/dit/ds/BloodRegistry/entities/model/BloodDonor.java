package gr.hua.dit.ds.BloodRegistry.entities.model;

import gr.hua.dit.ds.BloodRegistry.entities.Interfaces.IBloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.enums.BloodType;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Sex;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "blood_donors")
public class BloodDonor extends User implements IBloodDonor {

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
    @NotNull
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
    private String bloodwork;

    @Column
    private LocalDate lastDonationDate;

    public BloodDonor(String john, String doe, Sex sex, LocalDate of, BloodType bloodType, long l, String athens, String number, Object o, Object o1) {
    }

    public void setLastDonationDate(LocalDate lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }

    @OneToMany(mappedBy = "bloodDonor")
    private List<Donation> donations;

    @OneToOne(mappedBy = "bloodDonor", cascade = CascadeType.ALL)
    private Registration registration;

    @Override
    public BloodType getBloodType() {
        return this.bloodtype;
    }

    public void setBloodType(BloodType bloodtype) {
        this.bloodtype = bloodtype;
    }
}
