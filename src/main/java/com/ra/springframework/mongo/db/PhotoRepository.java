package com.ra.springframework.mongo.db;

import com.ra.springframework.mongo.domain.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends MongoRepository<Photo,String> {

}
