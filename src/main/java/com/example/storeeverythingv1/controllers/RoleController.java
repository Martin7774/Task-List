package com.example.storeeverythingv1.controllers;

import ch.qos.logback.core.model.Model;
import com.example.storeeverythingv1.data.Role;

import com.example.storeeverythingv1.repositories.RoleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/")
    public String getRoles(ModelMap model) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "roles";
    }
}
