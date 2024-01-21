package gr.hua.dit.ds.BloodRegistry.entities.model;

<<<<<<< HEAD
import gr.hua.dit.ds.BloodRegistry.entities.enums.Permission;
=======
import gr.hua.dit.ds.BloodRegistry.entities.Interfaces.IRole;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Permissions;
>>>>>>> backup-2b0394c
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.security.Permission;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "roles")
<<<<<<< HEAD
public class Role {
=======
public class Role implements IRole {
>>>>>>> backup-2b0394c

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column
    @NotEmpty
    @Size(min = 2, max = 50)
    private String name;

<<<<<<< HEAD
    @ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    private Set<Permission> permissions;

    // Optional: Converters to handle Set<Permission> to Set<String> and vice versa
    public Set<String> getPermissionsAsString() {
        return permissions.stream().map(Permission::name).collect(Collectors.toSet());
    }

    public void setPermissionsFromString(Set<String> permissionsString) {
        this.permissions = permissionsString.stream().map(Permission::valueOf).collect(Collectors.toSet());
    }

    public Role(String name) {
        this.name = name;
=======
    @ElementCollection(targetClass = Permissions.class)
    @CollectionTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    @NotNull // Add this annotation
    private Set<Permissions> permissions;

    @Override
    public void setPermissions(Set<Permissions> permissions) {
        this.permissions = permissions;
>>>>>>> backup-2b0394c
    }
}
