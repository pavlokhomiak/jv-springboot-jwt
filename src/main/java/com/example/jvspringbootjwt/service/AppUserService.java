package com.example.jvspringbootjwt.service;

import com.example.jvspringbootjwt.model.AppUser;
import com.example.jvspringbootjwt.model.AppRole;
import java.util.List;

public interface AppUserService {
    AppUser saveUser(AppUser user);
    AppRole saveRole(AppRole appRole);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
}
