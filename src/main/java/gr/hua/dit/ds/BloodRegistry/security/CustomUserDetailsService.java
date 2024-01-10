package gr.hua.dit.ds.BloodRegistry.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (isAdminAccount(user)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_BLOOD_DONOR"));
            authorities.add(new SimpleGrantedAuthority("ROLE_SECRETARIAT"));
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            // Roles for other users
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        }
        return new CustomUserPrincipal(user, authorities);
    }

    private boolean isAdminAccount(User user) {
        // Check if the user is the admin account
        return user.getEmail().equals("it218142@hua.gr");
    }
}
