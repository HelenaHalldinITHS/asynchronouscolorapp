package com.example;

import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static io.micronaut.http.HttpStatus.CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@MicronautTest
@TestInstance(PER_CLASS)
class ColorControllerTest implements TestPropertyProvider {

    @Inject
    ColorClient colorClient;

    @Test
    void colorEndpointInteractsWithMongo() {

        List<Color> colors = colorClient.findAll();
        assertTrue(colors.isEmpty());

        HttpStatus status = colorClient.save(new Color("red"));
        assertEquals(CREATED, status);

        colors = colorClient.findAll();
        assertFalse(colors.isEmpty());
        assertEquals("red", colors.get(0).getName());
        assertNull(colors.get(0).getSpecification());

        status = colorClient.save(new Color("Yellow", "Light and faded"));
        assertEquals(CREATED, status);

        colors = colorClient.findAll();
        assertTrue(colors.stream().anyMatch(f -> "Light and faded".equals(f.getSpecification())));
    }

    @AfterAll
    static void cleanup() {
        MongoDbUtils.closeMongoDb();
    }

    @Override
    public Map<String, String> getProperties() {
        MongoDbUtils.startMongoDb();
        return Collections.singletonMap("mongodb.uri", MongoDbUtils.getMongoDbUri());
    }
}
