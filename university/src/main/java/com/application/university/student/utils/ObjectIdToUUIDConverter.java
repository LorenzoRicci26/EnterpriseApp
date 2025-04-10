package com.application.university.student.utils;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ObjectIdToUUIDConverter implements Converter<ObjectId, UUID> {
    @Override
    public UUID convert(ObjectId source) {
        return UUID.nameUUIDFromBytes(source.toHexString().getBytes());
    }
}
