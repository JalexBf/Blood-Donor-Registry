package gr.hua.dit.ds.BloodRegistry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authz -> authz
                        .requestMatchers(new AntPathRequestMatcher("/", "GET"),
                                new AntPathRequestMatcher("/home", "GET")).permitAll() // Public URLs using requestMatchers
                        .anyRequest().authenticated() // Other URLs are secured
                )
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
