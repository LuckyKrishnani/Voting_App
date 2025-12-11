package com.example.votingbank.repository;

import com.example.votingbank.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Short> {
    Roles findByName(String name);
}
