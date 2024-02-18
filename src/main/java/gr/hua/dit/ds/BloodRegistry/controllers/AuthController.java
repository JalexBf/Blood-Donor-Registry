package gr.hua.dit.ds.BloodRegistry.controllers;

<<<<<<< Updated upstream
import gr.hua.dit.ds.BloodRegistry.config.JwtUtils;
import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.payload.request.LoginRequest;
import gr.hua.dit.ds.BloodRegistry.payload.response.JwtResponse;
import gr.hua.dit.ds.BloodRegistry.repositories.RoleRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.UserRepository;
import gr.hua.dit.ds.BloodRegistry.services.UserDetailsImpl;
import jakarta.annotation.PostConstruct;
=======

import gr.hua.dit.ds.BloodRegistry.payload.JwtResponse;
import gr.hua.dit.ds.BloodRegistry.payload.LoginRequest;
import gr.hua.dit.ds.BloodRegistry.repositories.RoleRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.UserRepository;
import gr.hua.dit.ds.BloodRegistry.security.JwtUtils;
import gr.hua.dit.ds.BloodRegistry.services.UserDetailsImpl;
>>>>>>> Stashed changes
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
<<<<<<< Updated upstream
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
=======
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
>>>>>>> Stashed changes
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
<<<<<<< Updated upstream
    @Autowired
    AuthenticationManager authenticationManager;//Interface from Spring Security for authenticate users.
=======

    @Autowired
    AuthenticationManager authenticationManager;
>>>>>>> Stashed changes

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
<<<<<<< Updated upstream
    PasswordEncoder encoder;
=======
    BCryptPasswordEncoder encoder;
>>>>>>> Stashed changes

    @Autowired
    JwtUtils jwtUtils;

<<<<<<< Updated upstream



=======
>>>>>>> Stashed changes
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("authentication");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        System.out.println("authentication: " + authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("post authentication");
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println("jw: " + jwt);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }
<<<<<<< Updated upstream

}
=======
}
>>>>>>> Stashed changes
