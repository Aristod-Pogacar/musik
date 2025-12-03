package com.muzik.muzik.service;

import org.springframework.stereotype.Service;

import com.muzik.muzik.Entity.Category;
import com.muzik.muzik.Entity.Music;
import com.muzik.muzik.Entity.User;
import com.muzik.muzik.Repository.MusicRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MusicService {

    private final MusicRepository musicRepository;
    public MusicService(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    public Music createMusic(Music music) {
        return musicRepository.save(music);
    }

    public List<Music> getAllMusic() {
        return musicRepository.findAll();
    }

    public Optional<Music> getMusicById(Long id) {
        return musicRepository.findById(id);
    }

    public Music updateMusic(Long id, Music musicDetails) {
        return musicRepository.findById(id)
                .map(music -> {
                    music.setTitre(musicDetails.getTitre());
                    music.setAlbum(musicDetails.getAlbum());
                    music.setFile(musicDetails.getFile());
                    music.setCategory(musicDetails.getCategory());
                    music.setUser(musicDetails.getUser());
                    return musicRepository.save(music);
                }).orElseThrow(() -> new RuntimeException("Music not found with id " + id));
    }

    public void deleteMusic(Long id) {
        musicRepository.deleteById(id);
    }

    public List<Music> getMusicByUser(User user) {
        return musicRepository.findByUser(user);
    }

    public List<Music> getMusicByCategory(Category category) {
        return musicRepository.findByCategory(category);
    }
}
