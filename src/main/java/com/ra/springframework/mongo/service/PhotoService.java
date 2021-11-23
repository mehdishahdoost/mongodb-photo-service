package com.ra.springframework.mongo.service;

import com.ra.springframework.mongo.db.PhotoRepository;
import com.ra.springframework.mongo.domain.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    public Optional<Photo> getById(String id)
    {
        //
        return this.photoRepository.findById(id);
    }

    public List<Photo> getAll()
    {
        //
        return this.photoRepository.findAll();
    }

    public Photo add(Photo photo) throws IOException
    {
        //
        return this.photoRepository.save(photo);
    }

}
