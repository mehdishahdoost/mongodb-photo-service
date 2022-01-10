package com.ra.springframework.mongo.db;

import com.ra.springframework.mongo.domain.Photo;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class PhotoRepositoryTest {

    @Container
    private static MongoDBContainer mongoDBContainer = new MongoDBContainer(
            DockerImageName.parse("mongo:4.0.10"));
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PhotoRepository photoRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeEach
    void beforeEach() throws Exception {
        //Save photo
        Photo mockPhoto1 = new Photo("01", "die Orchidee", new Binary(BsonBinarySubType.BINARY, new byte[1]));
        Photo mockPhoto2 = new Photo("02", "das Veilchen", new Binary(BsonBinarySubType.BINARY, new byte[1]));
        this.mongoTemplate.save(mockPhoto1);
        this.mongoTemplate.save(mockPhoto2);
    }

    @AfterEach
    void afterEach() {
        //Drop the collection
        this.mongoTemplate.dropCollection("photo");
    }

    @Test
    @DisplayName("Find All - Success")
    void testFindAllThenSuccess() {
        List<Photo> photoList = this.photoRepository.findAll();
        assertNotNull(photoList);
        assertEquals(2, photoList.size());
    }

    @Test
    @DisplayName("Final By Id - Successfully Found")
    void testFindByIdSuccess() {
        Optional<Photo> actualPhoto = this.photoRepository.findById("01");
        assertTrue(actualPhoto.isPresent(), "There should be a photo for ID 01");
        actualPhoto.ifPresent(photo -> {
            assertEquals("01", photo.getId());
            assertEquals("die Orchidee", photo.getTitle());
            assertEquals(BsonBinarySubType.BINARY.getValue(), photo.getFileContent().getType());
        });
    }

    @Test
    @DisplayName("Final By Id - Not Found")
    void testFindByIdFailure() {
        Optional<Photo> actualPhoto = this.photoRepository.findById("25");
        assertFalse(actualPhoto.isPresent(), "There should not find a photo for ID 25");
    }

    @Test
    @DisplayName("Save Photo - Success")
    void testSaveSuccess() {
        //Create a mock Photo and save to database
        Photo mockPhoto = new Photo("04", "das Schneeglöckchen", new Binary(BsonBinarySubType.BINARY, new byte[1]));
        Photo savedPhoto = this.photoRepository.save(mockPhoto);
        //Retrieve the Photo
        Optional<Photo> actualPhoto = this.photoRepository.findById("04");
        //Validation
        assertTrue(actualPhoto.isPresent(), "There should be a photo for ID 04");
        actualPhoto.ifPresent(photo -> {
            assertEquals("04", photo.getId(), "Photo id should be 01");
            assertEquals("das Schneeglöckchen", photo.getTitle(), "Photo title should be das Schneeglöckchen");
            assertEquals(BsonBinarySubType.BINARY.getValue(), photo.getFileContent().getType());
        });


    }


}
