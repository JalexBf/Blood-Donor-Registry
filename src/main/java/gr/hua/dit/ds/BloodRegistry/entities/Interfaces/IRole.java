package gr.hua.dit.ds.BloodRegistry.entities.Interfaces;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Permissions;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Roles;

import java.util.Set;

public interface IRole {

    Long getRoleId();
    Roles getName(); // Change the return type to Roles enum
    Set<Permissions> getPermissions();

    void setRoleId(Long roleId);
    void setName(Roles name); // Change the parameter type to Roles enum
    void setPermissions(Set<Permissions> permissions);
}