package com.example.jvspringbootjwt.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.example.jvspringbootjwt.auth.AppUserDetails;
import com.example.jvspringbootjwt.model.AppUser;
import com.example.jvspringbootjwt.model.AppRole;
import com.example.jvspringbootjwt.repo.AppUserRepo;
import com.example.jvspringbootjwt.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    private final AppUserRepo appUserRepo;
    private final RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Fetching user {}", username);
        AppUser appUser = appUserRepo.findByUsername(username);
        if (appUser == null) {
            log.error("User not found in the DB");
            throw new UsernameNotFoundException("User not found in database");
        } else {
            log.info("User found in the DB: {}", username);
        }
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        appUser.getAppRoles().forEach(role ->
            authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
        return new AppUserDetails(username, appUser.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving new user to the database");
        user.getPassword();
        return appUserRepo.save(user);
    }

    @Override
    public AppRole saveRole(AppRole appRole) {
        log.info("Saving new role {} to the database", appRole.getRoleName());
        return roleRepo.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Add role {} to user {}", roleName, username);
        AppUser appUser = appUserRepo.findByUsername(username);
        AppRole appRole = roleRepo.findByRoleName(roleName);
        if (appUser == null) {
            log.error("No such user");
            throw new RuntimeException(String.format("No user with name {}", username));
        }
        if (appRole == null) {
            log.error("No such role");
            throw new RuntimeException(String.format("No role with roleName {}", roleName));
        }
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Fetching user {}", username);
        return appUserRepo.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return appUserRepo.findAll();
    }
}
