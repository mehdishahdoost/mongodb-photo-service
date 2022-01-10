package com.ra.springframework.mongo.service;

import com.ra.springframework.mongo.db.PhotoRepository;
import com.ra.springframework.mongo.domain.Photo;
import com.ra.springframework.mongo.domain.dto.PhotoDto;
import com.ra.springframework.mongo.mapper.PhotoMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private PhotoMapper photoMapper;

    public PhotoService(PhotoRepository photoRepository, PhotoMapper photoMapper) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
    }

    public Optional<Photo> getById(String id) {
        return this.photoRepository.findById(id);
    }

    public List<Photo> getAll() {
        return this.photoRepository.findAll();
    }

    public PhotoDto add(Photo photo) {
        Photo savedPhoto = this.photoRepository.save(photo);
        return photoMapper.toPhotoDto(savedPhoto);
    }
}
