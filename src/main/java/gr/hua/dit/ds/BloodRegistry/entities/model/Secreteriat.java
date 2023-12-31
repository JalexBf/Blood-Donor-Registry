package gr.hua.dit.ds.BloodRegistry.entities.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper=false)
public class Secreteriat extends User {

    @Column
    @NotEmpty
    @Size(min = 2, max = 50)
    private String firstname;

    @Column
    @NotEmpty
    @Size(min = 2, max = 50)
    private String lastname;

    @OneToMany(mappedBy = "secreteriat", cascade = CascadeType.ALL)
    private List<Registration> registrations;

}
