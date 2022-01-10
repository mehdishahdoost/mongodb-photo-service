package com.ra.springframework.mongo.ctrl;

import com.ra.springframework.mongo.domain.Photo;
import com.ra.springframework.mongo.domain.dto.PhotoDto;
import com.ra.springframework.mongo.mapper.PhotoMapper;
import com.ra.springframework.mongo.service.PhotoService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PhotoControllerTest {

    @MockBean
    private PhotoService photoService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PhotoMapper photoMapper;

    @Test
    @DisplayName("POST /photo/add - Successfully Added")
    void testAddPhotoThenReturnOK() throws Exception {
        //Set up the mock service
        Photo mockPhoto = Photo.builder()
                .id("01")
                .title("das Schneeglöckchen")
                .fileContent(new Binary(BsonBinarySubType.BINARY , new byte[1])).build();
        PhotoDto photoDto = photoMapper.toPhotoDto(mockPhoto);
        Mockito.doReturn(photoDto).when(this.photoService).add(Mockito.any());

        //create file to upload
        MockMultipartFile multipartFile = new MockMultipartFile("image" , "snowdrop.gif" ,
                MediaType.IMAGE_GIF_VALUE , new byte[1]);

        //Execute the POST request using MockMvcRequestBuilders.multipart
        this.mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/photo/add").file(multipartFile)
                        .param("title" , "das Schneeglöckchen"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("das Schneeglöckchen"));
    }

    @Test
    @DisplayName("GET /photo/01 - Successfully Found")
    void testGetByIdThenFound() throws Exception{
        // Set up the mock service
        Photo photo = new Photo("01" , "das Schneeglöckchen" , new Binary(BsonBinarySubType.BINARY , new byte[1]));
        Mockito.doReturn(Optional.of(photo)).when(this.photoService).getById("01");
        //Execute the Get request
        this.mockMvc.perform(MockMvcRequestBuilders.get("/photo/{id}" , "01"))
                //Validate the response code and content type
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION , "/photo/01"))
                //Validate the returned fields
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("das Schneeglöckchen"));
    }

    @Test
    @DisplayName("GET /photo/01 - Not Found")
    void testGetByIdThenNotFound() throws Exception{
        // Set up the mock service
        Mockito.doReturn(Optional.empty()).when(this.photoService).getById("01");
        //Execute the Get request
        this.mockMvc.perform(MockMvcRequestBuilders.get("/photo/{id}" , "01"))
                //Validate the response code and content type
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}