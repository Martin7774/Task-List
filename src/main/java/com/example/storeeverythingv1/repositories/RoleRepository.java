package com.example.storeeverythingv1.repositories;

import com.example.storeeverythingv1.data.Role;
import com.example.storeeverythingv1.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
