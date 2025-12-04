package com.muzik.muzik.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.muzik.muzik.Entity.Category;
import com.muzik.muzik.service.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        return "categories/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/create";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes) {
        categoryService.saveCategory(category);
        redirectAttributes.addFlashAttribute("message", "Categorie créé avec succès !");
        return "redirect:/categories";
    }

    @GetMapping("/{id}")
    public String viewCategory(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Category category = categoryService.findCategoryById(id);
        if (category == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Categorie non trouvé.");
            return "redirect:/categories";
        }
        model.addAttribute("category", category);
        return "categories/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Category category = categoryService.findCategoryById(id);
        if (category == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Categorie non trouvé pour édition.");
            return "redirect:/categories";
        }
        model.addAttribute("category", category);
        return "categories/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateCategory(@PathVariable Long id, @ModelAttribute("category") Category category, RedirectAttributes redirectAttributes) {
        category.setId(id); // Ensure the ID from path variable is set to the user object
        categoryService.updateCategory(id, category); // Assuming this method handles the update logic
        redirectAttributes.addFlashAttribute("message", "Categorie mis à jour avec succès !");
        return "redirect:/categories";
    }

    @GetMapping("/{id}/delete") // Consider using @PostMapping for delete operations for better practice
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteCategory(id);
        redirectAttributes.addFlashAttribute("message", "Categorie supprimé avec succès !");
        return "redirect:/categories";
    }
}
