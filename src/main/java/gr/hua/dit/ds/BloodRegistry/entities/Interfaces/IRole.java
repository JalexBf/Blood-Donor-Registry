package gr.hua.dit.ds.BloodRegistry.entities.Interfaces;

import java.util.Set;

public interface IRole {

    // Getters
    Long getRoleId();

    String getName();

    Set<String> getPermissions();

    // Setters
    void setRoleId(Long roleId);

    void setName(String name);

    void setPermissions(Set<String> permissions);

}
