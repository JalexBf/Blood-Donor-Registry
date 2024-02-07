package gr.hua.dit.ds.BloodRegistry.entities.model;

import gr.hua.dit.ds.BloodRegistry.entities.Interfaces.IRole;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Permissions;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.security.Permission;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "roles")
public class Role implements IRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;

    @Column
    @NotEmpty
    @Size(min = 2, max = 50)
    private String name;

    @ElementCollection(targetClass = Permissions.class)
    @CollectionTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    @NotNull // Add this annotation
    private Set<Permissions> permissions;

    @Override
    public void setPermissions(Set<Permissions> permissions) {
        this.permissions = permissions;
    }
}
