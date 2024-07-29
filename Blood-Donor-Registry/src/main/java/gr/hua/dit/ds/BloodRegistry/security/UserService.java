package gr.hua.dit.ds.BloodRegistry.security;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();
}
