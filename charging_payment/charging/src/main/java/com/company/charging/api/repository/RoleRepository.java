package com.company.charging.api.repository;

import com.company.charging.api.model.Role;
import com.company.charging.api.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(RoleType roleType);
}
