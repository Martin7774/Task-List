package com.example.storeeverythingv1.controllers;

import ch.qos.logback.core.model.Model;
import com.example.storeeverythingv1.data.Role;
import com.example.storeeverythingv1.data.User;
import com.example.storeeverythingv1.repositories.CategoryRepository;
import com.example.storeeverythingv1.repositories.ItemRepository;
import com.example.storeeverythingv1.repositories.RoleRepository;
import com.example.storeeverythingv1.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String getUsers(ModelMap model) {
        List<User> users = userRepository.findAll();
        List<Role> roles = new ArrayList<>();
        for (User user : users) {
            roles = user.getRoles();
        }
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "users";
    }


    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users/";
    }
}
