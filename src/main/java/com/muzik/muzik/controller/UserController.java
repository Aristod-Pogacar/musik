package com.muzik.muzik.controller;

import com.muzik.muzik.Entity.User;
import com.muzik.muzik.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users/list"; // Renders src/main/resources/templates/users/list.html
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "users/create"; // Renders src/main/resources/templates/users/create.html
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("message", "Utilisateur créé avec succès !");
        return "redirect:/login";
    }

    @GetMapping("/{id}")
    public String viewUser(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.findUserById(id);
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Utilisateur non trouvé.");
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "users/view"; // Renders src/main/resources/templates/users/view.html
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.findUserById(id);
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Utilisateur non trouvé pour édition.");
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "users/edit"; // Renders src/main/resources/templates/users/edit.html
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        user.setId(id); // Ensure the ID from path variable is set to the user object
        userService.updateUser(id, user); // Assuming this method handles the update logic
        redirectAttributes.addFlashAttribute("message", "Utilisateur mis à jour avec succès !");
        return "redirect:/users";
    }

    @GetMapping("/{id}/delete") // Consider using @PostMapping for delete operations for better practice
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "Utilisateur supprimé avec succès !");
        return "redirect:/users";
    }
}

