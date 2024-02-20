package gr.hua.dit.ds.BloodRegistry.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "adminpassword";
        String storedEncodedPassword = "$2a$10$pk2f.9kkzOXLVWXbYCWOsulQbavAWqdfd7vy3MU0Qs5fSX.T6Yt6m";

        boolean isPasswordMatch = encoder.matches(rawPassword, storedEncodedPassword);
        System.out.println("Password match: " + isPasswordMatch);
    }
}

