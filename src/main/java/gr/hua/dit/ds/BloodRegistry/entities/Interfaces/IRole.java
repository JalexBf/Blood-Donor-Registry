package gr.hua.dit.ds.BloodRegistry.entities.Interfaces;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Permissions;
import java.util.Set;

public interface IRole {

    Long getRoleId();
    String getName();
    Set<Permissions> getPermissions(); // Updated return type

    void setRoleId(Long roleId);
    void setName(String name);
    void setPermissions(Set<Permissions> permissions);
}
