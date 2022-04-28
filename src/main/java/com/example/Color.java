package com.example;

import io.micronaut.core.annotation.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import javax.validation.constraints.NotBlank;

@Introspected
@ReflectiveAccess
public class Color {

    @NonNull
    @NotBlank
    @BsonProperty("name")
    private final String name;

    @Nullable
    @BsonProperty("specification")
    private final String specification;

    public Color(@NonNull String name) {
        this(name, null);
    }

    @Creator
    @BsonCreator
    public Color(@NonNull @BsonProperty("name") String name,
                 @Nullable @BsonProperty("specification") String specification) {
        this.name = name;
        this.specification = specification;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getSpecification() {
        return specification;
    }
}
