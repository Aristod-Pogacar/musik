package com.muzik.muzik.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.muzik.muzik.Entity.Category;
import com.muzik.muzik.Entity.Music;
import com.muzik.muzik.Entity.User;

public interface MusicRepository extends JpaRepository<Music, Long> {
    List<Music> findByUser(User user);
    List<Music> findByCategory(Category category);
}
