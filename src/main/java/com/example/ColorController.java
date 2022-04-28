package com.example;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static io.micronaut.http.HttpStatus.CONFLICT;
import static io.micronaut.http.HttpStatus.CREATED;


@Controller("/colors")
public class ColorController {

    private final ColorRepository colorRepository;

    public ColorController(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Get
    Publisher<Color> list(){
        return colorRepository.list();
    }

    @Post
    Mono<HttpStatus> save(@NonNull @NotNull @Valid Color color){
        return colorRepository.save(color)
                .map(added -> added ? CREATED : CONFLICT);
    }
}
