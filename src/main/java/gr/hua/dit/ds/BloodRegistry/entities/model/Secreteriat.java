package gr.hua.dit.ds.BloodRegistry.entities.model;

import gr.hua.dit.ds.BloodRegistry.entities.Interfaces.ISecreteriat;
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
public class Secreteriat extends User implements ISecreteriat {

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

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setAccountNonExpired(boolean accountNonExpired) {

    }

    @Override
    public void setAccountNonLocked(boolean accountNonLocked) {

    }

    @Override
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {

    }

    @Override
    public void setEnabled(boolean enabled) {

    }
}
