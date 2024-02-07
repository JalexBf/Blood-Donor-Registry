package gr.hua.dit.ds.BloodRegistry.security;

import gr.hua.dit.ds.BloodRegistry.DTO.SignInRequest;
import gr.hua.dit.ds.BloodRegistry.DTO.SignInResponse;
import gr.hua.dit.ds.BloodRegistry.DTO.UserDto;
import gr.hua.dit.ds.BloodRegistry.entities.model.BloodDonor;
import gr.hua.dit.ds.BloodRegistry.entities.model.Role;
import gr.hua.dit.ds.BloodRegistry.entities.model.Secretariat;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.repositories.RoleRepository;
import gr.hua.dit.ds.BloodRegistry.repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseCookie;

import java.time.LocalDate;

@Service
public class AuthenticationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired

    private JwtService jwtService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public User signUpBloodDonor(UserDto userDto){

        Role role =roleRepository.findById(userDto.getRole()).orElseThrow(()->new RuntimeException("role not found"));

        BloodDonor bloodDonor=BloodDonor.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .role(role)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .birthdate(userDto.getBirthdate())
                .bloodtype(userDto.getBloodType())
                .amka(userDto.getAmka())
                .phone(userDto.getPhone())
                .lastDonationDate(LocalDate.now())
                .sex(userDto.getSex())
                .region(userDto.getRegion())
                .build();
       return userRepository.saveAndFlush(bloodDonor);


    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public User signUpSecretariat(UserDto userDto){

        Role role =roleRepository.findById(userDto.getRole()).orElseThrow(()->new RuntimeException("role not found"));

        Secretariat secretariat = Secretariat.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .role(role)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .build();
        return userRepository.saveAndFlush(secretariat);


    }

    public SignInResponse signIn(SignInRequest request, HttpServletResponse response) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("user not found"));

        String jwt = jwtService.generateToken(user);



        ResponseCookie resCookie = ResponseCookie.from("token", jwt)
                .httpOnly(true)
                .sameSite("Lax")
                .secure(false)
                .path("/")
                .build();
        response.addHeader("Set-Cookie",resCookie.toString());

        return SignInResponse.builder().username(user.getUsername()).userId(user.getUserId()).roleId(user.getRole().getRoleId()).token("Bearer="+jwt).build();
    }
}
