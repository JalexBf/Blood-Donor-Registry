package gr.hua.dit.ds.BloodRegistry.entities.model;

import gr.hua.dit.ds.BloodRegistry.entities.Interfaces.IRegistration;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "registrations")
@Builder
public class Registration implements IRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long RegistrationId;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    @NotNull
    private LocalDate submissionDate;

    @OneToOne
    @JoinColumn(name = "blood_donor_id", unique = true, nullable = false)
    private BloodDonor bloodDonor;

    //@ManyToOne
    //@JoinColumn(name = "secretariat_id", nullable = true)
    //private Secretariat secretariat; // Make sure this matches the repository method


    public Registration(Status status) {
    }

}
