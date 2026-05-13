package com.ebanking.repository;

import com.ebanking.constant.RoleType;
import com.ebanking.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleName(RoleType roleName);
}
