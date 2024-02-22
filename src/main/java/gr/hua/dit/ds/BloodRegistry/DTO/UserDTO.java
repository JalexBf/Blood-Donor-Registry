package gr.hua.dit.ds.BloodRegistry.DTO;


import gr.hua.dit.ds.BloodRegistry.entities.model.User;

import java.time.LocalDate;
import java.util.Set;


public class UserDTO {
    private String username;
    private String password;
    private String email;
    private Set<String> roles; // Assuming roles are represented as a set of strings.
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private LocalDate passwordLastChangedDate;
    private int failedLoginAttempts;
    private LocalDate accountLockDate;


    public UserDTO() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDate getPasswordLastChangedDate() {
        return passwordLastChangedDate;
    }

    public void setPasswordLastChangedDate(LocalDate passwordLastChangedDate) {
        this.passwordLastChangedDate = passwordLastChangedDate;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public LocalDate getAccountLockDate() {
        return accountLockDate;
    }

    public void setAccountLockDate(LocalDate accountLockDate) {
        this.accountLockDate = accountLockDate;
    }


    public User toUser() {
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setEmail(this.email);
        user.setAccountNonExpired(this.accountNonExpired);
        user.setAccountNonLocked(this.accountNonLocked);
        user.setCredentialsNonExpired(this.credentialsNonExpired);
        user.setEnabled(this.enabled);
        user.setPasswordLastChangedDate(this.passwordLastChangedDate);
        user.setFailedLoginAttempts(this.failedLoginAttempts);
        user.setAccountLockDate(this.accountLockDate);

        return user;
    }
}
