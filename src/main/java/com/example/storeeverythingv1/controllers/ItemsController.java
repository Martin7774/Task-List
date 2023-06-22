package com.example.storeeverythingv1.controllers;

import com.example.storeeverythingv1.data.Category;
import com.example.storeeverythingv1.data.Item;
import com.example.storeeverythingv1.repositories.CategoryRepository;
import com.example.storeeverythingv1.repositories.ItemRepository;
import com.example.storeeverythingv1.repositories.UserRepository;
import com.example.storeeverythingv1.services.ItemService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/items")
public class ItemsController {
    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ItemsController(ItemService itemService, ItemRepository itemRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String getItems(Model model, Authentication authentication, HttpSession session) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Boolean isAscending = (Boolean) session.getAttribute("isAscending");
        String lastSorting = (String) session.getAttribute("lastSorting");
        if (isAscending == null) {
            isAscending = true;
        }
        if (lastSorting == null) {
            lastSorting = "deadline";
        }

        Long userId = userRepository.findUserIdByUsername(username);

        List<Item> items;
        if (Objects.equals(lastSorting, "deadline")) {
            System.out.println("Last is sorting by deadline");
            items = itemService.getItemsSortedByDeadline(!isAscending, false);
        } else {
            items = itemService.getItemsSortedByCategory();
        }
        model.addAttribute("items", items);
        return "items";
    }

    @GetMapping("/sort")
    public String sortItems(@RequestParam("column") String column, Model model, HttpSession session) {
        List<Item> sortedItems;
        Boolean isAscending = (Boolean) session.getAttribute("isAscending");
        if (isAscending == null) {
            isAscending = true;
        }

        if (column.equals("deadline")) {
            session.setAttribute("lastSorting", "deadline");
            sortedItems = itemService.getItemsSortedByDeadline(isAscending, true);
            session.setAttribute("isAscending", !isAscending);

        } else if (column.equals("category")) {
            session.setAttribute("lastSorting", "category");

            sortedItems = itemService.getItemsSortedByCategory();
        } else {
            return "items";
        }
        model.addAttribute("items", sortedItems);
        return "items";
    }

    @PostMapping("/filter")
    public String filterItemsByDateRange(@RequestParam("startDate") String startDateStr, @RequestParam("endDate") String endDateStr, Model model, HttpSession session) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        List<Item> filteredItems = itemService.getItemsSortedByDeadline(true, false)
                .stream()
                .filter(item -> {
                    LocalDate itemDeadline = item.getDeadline().toLocalDate();
                    return itemDeadline.isEqual(startDate) || itemDeadline.isEqual(endDate) || (itemDeadline.isAfter(startDate) && itemDeadline.isBefore(endDate));
                })
                .collect(Collectors.toList());

        model.addAttribute("items", filteredItems);
        return "items";
    }

    @GetMapping("/add")
    public String addItemForm(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Long userId = userRepository.findUserIdByUsername(username);
        Item item = new Item();
        item.setUser_id(userId);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("item", item);
        model.addAttribute("categories", categories);
        return "add-item";
    }

    @PostMapping("/add")
    public String addItemSubmit(@Valid @ModelAttribute Item item, BindingResult result, Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Long userId = userRepository.findUserIdByUsername(username);
        item.setUser_id(userId);
        if (result.hasErrors()) {
            List<Category> categories = categoryRepository.findAll();
            model.addAttribute("categories", categories);
            return "add-item";
        }
        itemRepository.save(item);
        return "redirect:/items/";
    }

    @GetMapping("/edit/{id}")
    public String editItemForm(@PathVariable("id") Long id, Model model) {

        Optional<Item> itemToEdit = itemRepository.findById(id);
        List<Category> categories = categoryRepository.findAll();
        if (itemToEdit.isPresent()) {
            model.addAttribute("item", itemToEdit.get());
            model.addAttribute("categories", categories);
            return "edit-item";
        } else {
            return "redirect:/items/";
        }
    }

    @PostMapping("/edit/{id}")
    public String editItemSubmit(@PathVariable("id") Integer id, @Valid @ModelAttribute Item item, BindingResult result, Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Long userId = userRepository.findUserIdByUsername(username);
        item.setUser_id(userId);

        if (result.hasErrors()) {
            List<Category> categories = categoryRepository.findAll();
            model.addAttribute("item", item);
            model.addAttribute("categories", categories);
            return "edit-item";
        }
        item.setId(id);
        itemRepository.save(item);
        return "redirect:/items/";
    }

    @PostMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return "redirect:/items/";
    }
}