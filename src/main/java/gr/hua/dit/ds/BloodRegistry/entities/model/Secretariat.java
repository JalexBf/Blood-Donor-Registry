package gr.hua.dit.ds.BloodRegistry.entities.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr.hua.dit.ds.BloodRegistry.entities.Interfaces.ISecreteriat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
@Table(name = "secretariats")

public class Secretariat extends User implements ISecreteriat {

    @Column
    @NotEmpty
    @Size(min = 2, max = 50)
    private String firstname;

    @Column
    @NotEmpty
    @Size(min = 2, max = 50)
    private String lastname;

    //@OneToMany(mappedBy = "secretariat", cascade = CascadeType.ALL)
    //@JsonIgnore
    //private List<Registration> registrations;


}
