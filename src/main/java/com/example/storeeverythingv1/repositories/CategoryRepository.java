package com.example.storeeverythingv1.repositories;

import com.example.storeeverythingv1.data.Category;
import com.example.storeeverythingv1.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Dodatkowe metody specyficzne dla repozytorium użytkowników
}



