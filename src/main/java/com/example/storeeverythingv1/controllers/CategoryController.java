package com.example.storeeverythingv1.controllers;

import com.example.storeeverythingv1.data.Category;
import com.example.storeeverythingv1.repositories.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public String getCategories(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/add")
    public String addCategoryForm(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "add-category";
    }

    @PostMapping("/add")
    public String addCategorySubmit(@Valid @ModelAttribute Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("category", category);
            return "add-category";
        }
        categoryRepository.save(category);
        return "redirect:/categories/";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        Optional<Category> categoryToEdit = categoryRepository.findById(id);
        if (categoryToEdit.isPresent()) {
            model.addAttribute("category", categoryToEdit.get());
            return "edit-category";
        } else {
            return "redirect:/categories/";
        }
    }

    @PostMapping("/edit/{id}")
    public String editCategorySubmit(@PathVariable Integer id, @Valid @ModelAttribute Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("category", category);
            return "edit-category";
        }
        category.setId(id);
        categoryRepository.save(category);
        return "redirect:/categories/";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/categories/";
    }
}