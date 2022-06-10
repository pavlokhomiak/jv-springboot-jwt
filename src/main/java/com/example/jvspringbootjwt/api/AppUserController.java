package com.example.jvspringbootjwt.api;

import com.example.jvspringbootjwt.dto.RoleToUserForm;
import com.example.jvspringbootjwt.model.AppUser;
import com.example.jvspringbootjwt.model.AppRole;
import com.example.jvspringbootjwt.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(appUserService.getUsers());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        // server path
        URI uri = URI.create(ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/users").toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveUser(user));
    }

    @PostMapping("/role")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<AppRole> saveRole(@RequestBody AppRole appRole) {
        URI uri = URI.create(ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/users/role").toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveRole(appRole));
    }

    @PostMapping("/user/role")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN','ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        appUserService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}
