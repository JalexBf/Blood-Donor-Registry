package gr.hua.dit.ds.BloodRegistry.controllers;

import gr.hua.dit.ds.BloodRegistry.DTO.SignInRequest;
import gr.hua.dit.ds.BloodRegistry.DTO.SignInResponse;
import gr.hua.dit.ds.BloodRegistry.DTO.UserDto;
import gr.hua.dit.ds.BloodRegistry.entities.model.User;
import gr.hua.dit.ds.BloodRegistry.security.AuthenticationService;
import gr.hua.dit.ds.BloodRegistry.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;


    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }


    @GetMapping
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> editUser (@PathVariable Long id,@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(userDto,id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signUpBloodDonor")
    public ResponseEntity<User> signUpBloodDonor(@RequestBody UserDto userDto){
        return ResponseEntity.ok(authenticationService.signUpBloodDonor(userDto));
    }

    @PostMapping("/signUpSecretariat")
    public ResponseEntity<User> signUpSecretariat(@RequestBody UserDto userDto){
        return ResponseEntity.ok(authenticationService.signUpSecretariat(userDto));
    }



    @PostMapping("/signIn")
    private ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest, HttpServletResponse response){
        return ResponseEntity.ok(authenticationService.signIn(signInRequest,response));
    }
}
