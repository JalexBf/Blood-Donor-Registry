package gr.hua.dit.ds.BloodRegistry.entities.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long userId;

    @Column
    @NotEmpty(message = "Username is required")
    @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters")
    private String username;

    @Column
    @NotEmpty(message = "Password is required")
    @JsonIgnore
    private String password;

    @Column
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;


    // One role per user
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(nullable = true)
    @JsonIgnore
    private boolean isAccountNonExpired;
    @JsonIgnore
    @Column(nullable = true)
    private boolean isAccountNonLocked;
    @JsonIgnore
    @Column(nullable = true)
    private boolean isCredentialsNonExpired;
    @JsonIgnore
    @Column(nullable = true)
    private boolean isEnabled;
    @JsonIgnore
    @Column(nullable = true)
    private LocalDate passwordLastChangedDate;
    @JsonIgnore
    @Column(nullable = true)
    private int failedLoginAttempts;
    @JsonIgnore
    @Column(nullable = true)
    private LocalDate accountLockDate;
    @Column(nullable = true)

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }
}
