package gr.hua.dit.ds.BloodRegistry.security;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordHashing {
    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword = "adminpassword";
        String hashedPassword = encoder.encode(rawPassword);
        System.out.println("Hashed password: " + hashedPassword);
    }
}

