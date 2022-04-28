package com.example;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Singleton
public class MongoDbColorRepository implements ColorRepository{

    private final MongoDbConfiguration mongoConf;
    private final MongoClient mongoClient;

    public MongoDbColorRepository(MongoDbConfiguration mongoConf, MongoClient mongoClient) {
        this.mongoConf = mongoConf;
        this.mongoClient = mongoClient;
    }

    @NonNull
    @Override
    public Publisher<Color> list() {
        return getCollection().find();
    }

    @Override
    public Mono<Boolean> save(@NonNull @NotNull @Valid Color color) {
        return Mono.from(getCollection().insertOne(color))
                .map(insertOneResult -> true)
                .onErrorReturn(false);
    }

    @NonNull
    private MongoCollection<Color> getCollection(){
        return mongoClient.getDatabase(mongoConf.getName())
                .getCollection(mongoConf.getCollection(), Color.class);
    }
}
