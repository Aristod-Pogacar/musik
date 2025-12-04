package com.muzik.muzik.controller;

import com.muzik.muzik.Entity.Category;
import com.muzik.muzik.Entity.Music;
import com.muzik.muzik.Entity.User;
import com.muzik.muzik.Repository.UserRepository;
import com.muzik.muzik.service.CategoryService;
import com.muzik.muzik.service.MusicService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/music")
public class MusicController {

    private final MusicService musicService;
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    public MusicController(MusicService musicService, UserRepository userRepository, CategoryService categoryService) {
        this.musicService = musicService;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getAllSongs(Model model) {
        model.addAttribute("musics", musicService.getAllMusic());
        return "music/list";
    }

    @GetMapping("/{id}")
    public String getSongDetails(@PathVariable Long id, Model model) {
        musicService.getMusicById(id).ifPresent(music -> model.addAttribute("music", music));
        return "music/view";
    }

    @GetMapping("/user/{userId}")
    public String getMusicByUser(@PathVariable Long userId, Model model) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Music> musics = musicService.getMusicByUser(user);
        model.addAttribute("musics", musics);
        model.addAttribute("user", user);
        return "music/user-music";
    }

    @GetMapping("/category/{categoryId}")
    public String getMusicByCategory(@PathVariable Long categoryId, Model model) {
        Category category = categoryService.findCategoryById(categoryId);
        List<Music> musics = musicService.getMusicByCategory(category);
        model.addAttribute("musics", musics);
        model.addAttribute("category", category);
        return "music/category-music";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("music", new Music());
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("users", userRepository.findAll());
        return "music/create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("music") Music music,
                            @RequestParam("audioFile") MultipartFile audioFile,
                            RedirectAttributes redirectAttributes) {

        if (!audioFile.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + audioFile.getOriginalFilename();
                Path uploadPath = Paths.get("uploads");

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Files.copy(audioFile.getInputStream(),
                        uploadPath.resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING);

                // ✅ ON STOCKE LE CHEMIN STRING
                music.setFile("/uploads/" + fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        musicService.createMusic(music);
        redirectAttributes.addFlashAttribute("message", "Music créée avec succès !");
        return "redirect:/music/";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Music> music = musicService.getMusicById(id);
        if (music.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Music non trouvé pour édition.");
            return "redirect:/music/";
        }
        model.addAttribute("music", music.get());
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("users", userRepository.findAll());
        return "music/edit";
    }

    @PostMapping("/{id}")
    public String updateMusic(@PathVariable Long id, @ModelAttribute("music") Music music, RedirectAttributes redirectAttributes) {
        music.setId(id); // Ensure the ID from path variable is set to the music object
        musicService.updateMusic(id, music); // Assuming this method handles the update logic
        redirectAttributes.addFlashAttribute("message", "Music mis à jour avec succès !");
        return "redirect:/music/";
    }

    @GetMapping("/{id}/delete") // Consider using @PostMapping for delete operations for better practice
    public String deleteMusic(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        musicService.deleteMusic(id);
        redirectAttributes.addFlashAttribute("message", "Music supprimé avec succès !");
        return "redirect:/music/";
    }

}
