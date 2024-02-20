package gr.hua.dit.ds.BloodRegistry.entities.model;

import gr.hua.dit.ds.BloodRegistry.entities.Interfaces.IApplication;
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
@Table(name = "applications")
public class Application implements IApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long applicationId;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    @NotNull
    private LocalDate submissionDate;

    @OneToOne
    @JoinColumn(name = "blood_donor_id", unique = true, nullable = false)
    private BloodDonor bloodDonor;

    @ManyToOne
    @JoinColumn(name = "secretariat_id", nullable = false)
    private Secretariat secretariat;


    @Override
    public Long getapplicationId() {
        return null;
    }


    @Override
    public Secretariat getSecretariat() {
        return null;
    }


    @Override
    public void setapplicationId(Long id) {

    }


    @Override
    public void setSecretariat(Secretariat secretariat) {

    }
}
