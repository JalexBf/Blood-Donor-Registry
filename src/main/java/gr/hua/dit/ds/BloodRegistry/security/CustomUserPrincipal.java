package gr.hua.dit.ds.BloodRegistry.security;

import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserPrincipal implements UserDetails {
    private final User user;
    private final Collection<? extends GrantedAuthority> authorities;


    public CustomUserPrincipal(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Add roles to authorities
        for (Role role : user.getRoles()) {
            authorities.add(() -> "ROLE_" + role.getName());

            // Add permissions to authorities
            authorities.addAll(role.getPermissions().stream()
                    .map(permission -> (GrantedAuthority) () -> permission)
                    .collect(Collectors.toList()));
        }
        return authorities;
    }


    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

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


}

