package com.ra.springframework.mongo.mapper;

import com.ra.springframework.mongo.domain.Photo;
import com.ra.springframework.mongo.domain.dto.PhotoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PhotoMapper {
    PhotoMapper INSTANCE = Mappers.getMapper(PhotoMapper.class);
    PhotoDto toPhotoDto(Photo photo);
}
