package com.muzik.muzik.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muzik.muzik.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByNom(String nom);
}
