package com.ra.springframework.mongo.service;

import com.ra.springframework.mongo.db.PhotoRepository;
import com.ra.springframework.mongo.domain.Photo;
import com.ra.springframework.mongo.domain.dto.PhotoDto;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PhotoServiceTest {

    @Autowired
    private PhotoService photoService;

    @MockBean
    private PhotoRepository photoRepository;

    @Test
    @DisplayName("Test findById Success")
    void findById() {

        //Setup mock
        Photo mockPhoto = new Photo("01" , "die Orchidee" , new Binary(BsonBinarySubType.BINARY , new byte[1]));
        Mockito.doReturn(Optional.of(mockPhoto)).when(this.photoRepository).findById("01");

        //Execute the service call
        Optional<Photo> actualPhoto = this.photoService.getById("01");

        //Assert the response
        assertSame(actualPhoto.get() , mockPhoto , "Photo should be same");
        assertEquals(actualPhoto.get().getTitle() , mockPhoto.getTitle());

    }

    @Test
    @DisplayName("Test findAll Success")
    void findAll() {

        //Setup mock
        Photo mockPhoto1 = new Photo("01" , "die Orchidee" , new Binary(BsonBinarySubType.BINARY , new byte[1]));
        Photo mockPhoto2 = new Photo("02" , "das Veilchen" , new Binary(BsonBinarySubType.BINARY , new byte[1]));
        Mockito.doReturn(Arrays.asList(mockPhoto1,mockPhoto2)).when(this.photoRepository).findAll();

        //Execute the service call
        List<Photo> actualPhotoList = this.photoService.getAll();

        //Assert the response
        assertEquals(2 , actualPhotoList.size() , "getAll should return 2 photo");

    }

    @Test
    @DisplayName("Test addPhoto Success")
    void addPhoto() throws Exception {

        //Setup mock
        Photo mockPhoto = new Photo("01" , "die Orchidee" , new Binary(BsonBinarySubType.BINARY , new byte[1]));
        Mockito.doReturn(mockPhoto).when(this.photoRepository).save(Mockito.any());

        //Execute the service call
        PhotoDto actualPhoto = this.photoService.add(mockPhoto);

        //Assert the response
        assertEquals("die Orchidee" , actualPhoto.getTitle() , "The title for the actual photo should be gifPhoto");
    }
}