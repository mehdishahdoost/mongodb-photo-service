package com.ra.springframework.mongo.ctrl;

import com.ra.springframework.mongo.domain.Photo;
import com.ra.springframework.mongo.domain.dto.PhotoDto;
import com.ra.springframework.mongo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/photo")
@BasePathAwareController
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping(value = "/add" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PhotoDto> addPhoto(@RequestParam("title") String title, @RequestParam("image") MultipartFile file) throws IOException
    {
        //Create and add new photo
        Photo postPhoto = Photo.builder().title(title).fileContent(new Binary(BsonBinarySubType.BINARY,file.getBytes())).build();
        PhotoDto photo = this.photoService.add(postPhoto);
        try {
            return ResponseEntity
                    .created(new URI("/photo/" + photo.getId()))
                    .body(photo);
        }catch (URISyntaxException exception)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll()
    {
        List<Photo> photoList = this.photoService.getAll();
        if (!photoList.isEmpty()) {
            try {
                return ResponseEntity.ok().location(new URI("/photo/")).body(photoList);
            } catch (URISyntaxException exception) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        else
            return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable String id)
    {
        return this.photoService.getById(id)
                .map(photo ->
                {
                    try
                    {
                        return ResponseEntity.ok().location(new URI("/photo/" + photo.getId())).body(photo);
                    }catch(URISyntaxException exception)
                    {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                }).orElse(ResponseEntity.notFound().build());
    }


}
