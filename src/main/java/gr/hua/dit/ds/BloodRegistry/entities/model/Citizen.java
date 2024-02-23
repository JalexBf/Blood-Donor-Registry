package gr.hua.dit.ds.BloodRegistry.entities.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;


@Entity
@EqualsAndHashCode(callSuper=false)
@DiscriminatorValue("Citizen")
public class Citizen extends User {
    public Citizen() {
        super();
    }
}
