package com.example.jvspringbootjwt;

import com.example.jvspringbootjwt.model.AppUser;
import com.example.jvspringbootjwt.model.AppRole;
import com.example.jvspringbootjwt.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class JvSpringbootJwtApplication {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(JvSpringbootJwtApplication.class, args);
    }

    @Bean
    CommandLineRunner run(AppUserService userService) {
        return args -> {
          userService.saveRole(new AppRole(null, "ROLE_USER"));
          userService.saveRole(new AppRole(null, "ROLE_MANAGER"));
          userService.saveRole(new AppRole(null, "ROLE_ADMIN"));
          userService.saveRole(new AppRole(null, "ROLE_SUPER_ADMIN"));

          userService.saveUser(new AppUser(null, "Bob", "bob", passwordEncoder.encode("p"), new ArrayList<>()));
          userService.saveUser(new AppUser(null, "Alisa", "alisa", passwordEncoder.encode("p"), new ArrayList<>()));
          userService.saveUser(new AppUser(null, "Pavlo", "pavlo", passwordEncoder.encode("p"), new ArrayList<>()));
          userService.saveUser(new AppUser(null, "Ben", "ben", passwordEncoder.encode("p"), new ArrayList<>()));

          userService.addRoleToUser("bob", "ROLE_USER");
          userService.addRoleToUser("bob", "ROLE_ADMIN");
          userService.addRoleToUser("bob", "ROLE_SUPER_ADMIN");
          userService.addRoleToUser("alisa", "ROLE_USER");
          userService.addRoleToUser("alisa", "ROLE_MANAGER");
          userService.addRoleToUser("pavlo", "ROLE_MANAGER");
          userService.addRoleToUser("ben", "ROLE_ADMIN");
        };
    }

}
