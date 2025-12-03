package com.muzik.muzik.service;

import java.util.List;

import com.muzik.muzik.Repository.CategoryRepository;
import com.muzik.muzik.Entity.Category;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category updateCategory(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory != null) {
            existingCategory.setNom(category.getNom());
            return categoryRepository.save(existingCategory);
        }
        return null;
    }

    public Category findCategoryByNom(String nom) {
        return categoryRepository.findByNom(nom);
    }
}
