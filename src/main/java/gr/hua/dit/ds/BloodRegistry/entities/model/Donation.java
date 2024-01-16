package gr.hua.dit.ds.BloodRegistry.entities.model;

import gr.hua.dit.ds.BloodRegistry.entities.Interfaces.IBloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.Interfaces.IDonation;
import gr.hua.dit.ds.BloodRegistry.entities.enums.BloodType;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Sex;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper=false)
public class Donation implements IDonation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long donationId;

    @Column
    @NotNull
    private LocalDate donationDate;

    @ManyToOne
    @JoinColumn(name = "blood_donor_id, nullable = false")
    private BloodDonor bloodDonor;


}
