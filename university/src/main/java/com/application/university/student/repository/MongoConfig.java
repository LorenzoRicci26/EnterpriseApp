package com.application.university.student.repository;

import com.application.university.student.utils.ObjectIdToUUIDConverter;
import com.application.university.student.utils.UUIDToObjectIdConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Override
    protected String getDatabaseName() {
        return "university";
    }

    @Override
    public MongoCustomConversions customConversions() {
        List<Object> converters = new ArrayList<>();
        converters.add(new ObjectIdToUUIDConverter());
        converters.add(new UUIDToObjectIdConverter());
        return new MongoCustomConversions(converters);
    }
}
