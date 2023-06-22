package com.example.storeeverythingv1.repositories;

import com.example.storeeverythingv1.data.Item;
import com.example.storeeverythingv1.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // Dodatkowe metody specyficzne dla repozytorium użytkowników
}
