package gr.hua.dit.ds.BloodRegistry.entities.Interfaces;


public interface IUser {

    // Getters
    Long getUserId();
    String getUsername();
    String getPassword();
    String getEmail();
    boolean isAccountNonExpired();
    boolean isAccountNonLocked();
    boolean isCredentialsNonExpired();
    boolean isEnabled();

    // Setters
    void setUserId(Long id);
    void setUsername(String username);
    void setPassword(String password);
    void setEmail(String email);

    void setAccountNonExpired(boolean accountNonExpired);
    void setAccountNonLocked(boolean accountNonLocked);
    void setCredentialsNonExpired(boolean credentialsNonExpired);
    void setEnabled(boolean enabled);
}