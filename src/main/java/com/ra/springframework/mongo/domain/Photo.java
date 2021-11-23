package com.ra.springframework.mongo.domain;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "photo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photo
{
    @Id
    private String id;
    private String title;
    private Binary fileContent;

}
