package com.starkend.reactortest.person;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Log4j2
@Data
@Component
public class PersonHandler {

    private static final String NAME_PARAM = "name";
    private static final String ID_PARAM = "id";
    private final PersonRepository repository;

    public PersonHandler(PersonRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> listPeople(ServerRequest request) {
        Flux<Person> people = repository.findAll();

        return ok().contentType(MediaType.APPLICATION_JSON).body(people, Person.class);
    }

    public Mono<ServerResponse> getAllUserInfoFormatted(ServerRequest request) {
        Flux<String> userInfo = repository.findAll().map(
                user -> String.format("%s-%s  ", user.getId(), user.getName()));
        return ok().contentType(MediaType.APPLICATION_JSON).body(userInfo, String.class);
    }

    public Mono<ServerResponse> getAllUserNames(ServerRequest request) {
        Flux<String> people = repository.findAll().map(
                person -> String.format("%s ", person.getName()));
        return ok().contentType(MediaType.APPLICATION_JSON).body(people, String.class);
    }

    public Mono<ServerResponse> getAllUserIds(ServerRequest request) {
        Flux<Long> ids = repository.findAll().map(
                id -> id.getId());

        return ok().contentType(MediaType.APPLICATION_JSON).body(ids, Long.class);
    }

    public Mono<ServerResponse> getPersonById(ServerRequest request) {
        String id = request.queryParam(ID_PARAM).get();
        return repository.findById(Long.valueOf(id))
                .flatMap(person -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(person))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createPerson(ServerRequest request) {
        Person person = new Person(Long.valueOf(request.queryParam(ID_PARAM).get()), request.queryParam(NAME_PARAM).get());

        return repository.save(person)
                .flatMap(savedPerson -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(savedPerson))
                .switchIfEmpty(ServerResponse.unprocessableEntity().build());
    }

    public Mono<ServerResponse> existsById(ServerRequest request) {
        String id = request.queryParam(ID_PARAM).get();
        Mono<Boolean> exists = repository.existsById(Long.valueOf(id));

        return ok().contentType(MediaType.APPLICATION_JSON).body(exists, Boolean.class);
    }

    public Mono<ServerResponse> count(ServerRequest request) {
        Mono<Long> count = repository.count();

        return ok().contentType(MediaType.APPLICATION_JSON).body(count, Long.class);
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        String id = request.queryParam(ID_PARAM).get();
        Mono<Void> result = repository.deleteById(Long.valueOf(id));

        return ok().contentType(MediaType.APPLICATION_JSON).body(result, Void.class);
    }
}
