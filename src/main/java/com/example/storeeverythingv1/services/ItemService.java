package com.example.storeeverythingv1.services;

import com.example.storeeverythingv1.data.Item;
import com.example.storeeverythingv1.repositories.ItemRepository;
import com.example.storeeverythingv1.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public List<Item> getItemsSortedByDeadline(Boolean isAscending, Boolean trueSorting) {
        Long idOfUser = getIdOfUser();
        List<Item> items = getUserItemsById(idOfUser);

        items.sort(Comparator.comparing(Item::getDeadline));

        if (!isAscending) {
            Collections.reverse(items);
        }
        return items;
    }

    public List<Item> getItemsSortedByCategory() {
        Long idOfUser = getIdOfUser();
        List<Item> items = getUserItemsById(idOfUser);

        Map<String, Integer> categoryCountMap = new HashMap<>();
        for (Item item : items) {
            String categoryName = item.getCategory().getName();
            categoryCountMap.put(categoryName, categoryCountMap.getOrDefault(categoryName, 0) + 1);
        }

        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(categoryCountMap.entrySet());
        sortedEntries.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        List<Item> sortedItems = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            String categoryName = entry.getKey();
            for (Item item : items) {
                if (item.getCategory().getName().equals(categoryName)) {
                    sortedItems.add(item);
                }
            }
        }
        return sortedItems;
    }

    public List<Item> filterItemsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        Long idOfUser = getIdOfUser();
        List<Item> items = getUserItemsById(idOfUser);

        List<Item> filteredItems = new ArrayList<>();
        for (Item item : items) {
            LocalDateTime itemDate = item.getDeadline();

            if (itemDate.compareTo(startDate) >= 0 && itemDate.compareTo(endDate) <= 0) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }

    private Long getIdOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Long userId = userRepository.findUserIdByUsername(username);
        return userId;
    }

    public List<Item> getUserItemsById(Long userId) {
        List<Item> items = itemRepository.findAll();


        List<Item> userItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getUser_id() == userId) {
                userItems.add(item);
            }
        }

        return userItems;
    }
}
