package com.example.jvspringbootjwt.repo;

import com.example.jvspringbootjwt.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<AppRole, Long> {
    AppRole findByRoleName(String name);
}
