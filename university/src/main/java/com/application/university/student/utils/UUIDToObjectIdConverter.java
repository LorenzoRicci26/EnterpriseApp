package com.application.university.student.utils;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDToObjectIdConverter implements Converter<UUID, ObjectId> {

    @Override
    public ObjectId convert(UUID source) {
        return new ObjectId(source.toString()
                .replace("-", "")
                .substring(0, 24));
    }
}
