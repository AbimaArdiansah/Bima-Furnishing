package com.bimafurnishing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bimafurnishing.model.Furniture;
import com.bimafurnishing.repository.FurnitureRepository;

@Controller
public class FurnitureController {

    @Autowired
    private FurnitureRepository furnitureRepository;

    // ================= CLIENT ROUTES (READ-ONLY) =================
    @GetMapping("/")
    public String clientHome(@RequestParam(value = "category", required = false) String category, Model model) {
        List<Furniture> list;
        if (category != null && !category.isEmpty()) {
            list = furnitureRepository.findAll().stream()
                    .filter(f -> f.getCategory().equalsIgnoreCase(category))
                    .toList();
        } else {
            list = furnitureRepository.findAll();
        }
        model.addAttribute("furnitures", list);
        return "client";
    }

    // ================= ADMIN ROUTES (FULL CRUD) =================
    // READ (Dashboard Admin)
    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("furnitures", furnitureRepository.findAll());
        model.addAttribute("furniture", new Furniture());
        return "admin";
    }

    // CREATE & UPDATE
    @PostMapping("/admin/save")
    public String saveFurniture(@ModelAttribute("furniture") Furniture furniture) {
        furnitureRepository.save(furniture);
        return "redirect:/admin";
    }

    // READ SINGLE (Form Edit)
    @GetMapping("/admin/edit/{id}")
    public String editFurniture(@PathVariable Long id, Model model) {
        model.addAttribute("furniture", furnitureRepository.findById(id).orElse(null));
        model.addAttribute("furnitures", furnitureRepository.findAll());
        return "admin";
    }

    // DELETE
    @GetMapping("/admin/delete/{id}")
    public String deleteFurniture(@PathVariable Long id) {
        furnitureRepository.deleteById(id);
        return "redirect:/admin";
    }
}