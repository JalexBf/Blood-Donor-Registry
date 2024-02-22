package gr.hua.dit.ds.BloodRegistry.entities.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@EqualsAndHashCode(callSuper=false)
@DiscriminatorValue("Admin")
public class Admin extends User {

    public Admin() {
        super();
    }
}