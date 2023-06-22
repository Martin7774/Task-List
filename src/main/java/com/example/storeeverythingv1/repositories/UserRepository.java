package com.example.storeeverythingv1.repositories;

import com.example.storeeverythingv1.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Long findUserIdByUsername(@Param("email") String email);
    User findByEmail(String email);
}
