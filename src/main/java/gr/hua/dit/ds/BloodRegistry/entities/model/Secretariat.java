package gr.hua.dit.ds.BloodRegistry.entities.model;

import gr.hua.dit.ds.BloodRegistry.entities.Interfaces.ISecretariat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "Secretariats")
public class Secretariat extends User implements ISecretariat {

    @Column
    @NotEmpty
    @Size(min = 2, max = 50)
    private String firstname;

    @Column
    @NotEmpty
    @Size(min = 2, max = 50)
    private String lastname;


    @OneToMany(mappedBy = "secretariat")
    private Set<Application> applications = new HashSet<>();


    public Long getSecretariatId() {

        return this.getUserId();
    }


    @Override
    public List<Application> getapplications() {
        return null;
    }


    @Override
    public void setapplications(List<Application> applications) {

    }
}
