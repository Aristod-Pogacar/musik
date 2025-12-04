package com.muzik.muzik.controller;

import com.muzik.muzik.service.CategoryService;
import com.muzik.muzik.service.MusicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/player")
public class PlayerController {

    private final MusicService musicService;
    private final CategoryService categoryService;

    public PlayerController(MusicService musicService, CategoryService categoryService) {
        this.musicService = musicService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showPlayer(Model model) {
        model.addAttribute("musics", musicService.getAllMusic());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "player/index";
    }

    @GetMapping("/category/{categoryId}")
    public String showPlayerByCategory(@PathVariable Long categoryId, Model model) {
        var category = categoryService.findCategoryById(categoryId);
        model.addAttribute("musics", musicService.getMusicByCategory(category));
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("selectedCategory", category);
        return "player/index";
    }
}
