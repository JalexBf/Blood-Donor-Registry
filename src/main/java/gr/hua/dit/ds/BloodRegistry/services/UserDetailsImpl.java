package gr.hua.dit.ds.BloodRegistry.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;

    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    private LocalDate passwordLastChangedDate;
    private int failedLoginAttempts;
    private LocalDate accountLockDate;
    private boolean enabled;

    public UserDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities,
                           LocalDate passwordLastChangedDate, int failedLoginAttempts,
                           LocalDate accountLockDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.passwordLastChangedDate = passwordLastChangedDate;
        this.failedLoginAttempts = failedLoginAttempts;
        this.accountLockDate = accountLockDate;
    }


    // One role per user
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));

        return new UserDetailsImpl(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                user.getPasswordLastChangedDate(),
                user.getFailedLoginAttempts(),
                user.getAccountLockDate());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        return passwordLastChangedDate.isAfter(oneYearAgo);
    }

    @Override
    public boolean isAccountNonLocked() {
        if (failedLoginAttempts >= 3) {
            return accountLockDate.isBefore(LocalDate.now());
        }
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        LocalDate ninetyDaysAgo = LocalDate.now().minusDays(90);
        return passwordLastChangedDate.isAfter(ninetyDaysAgo);
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
