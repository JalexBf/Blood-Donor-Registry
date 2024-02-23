package gr.hua.dit.ds.BloodRegistry.entities.Interfaces;

import gr.hua.dit.ds.BloodRegistry.entities.enums.Permissions;
import gr.hua.dit.ds.BloodRegistry.entities.enums.Roles;

import java.util.Set;

public interface IRole {

    Long getRoleId();
    Roles getName();

    void setRoleId(Long roleId);
    void setName(Roles name);
}