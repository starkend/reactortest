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

    private final PersonRepository repository;

    private static final String NAME_PARAM = "name";
    private static final String ID_PARAM = "id";

    public PersonHandler(PersonRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> listPeople(ServerRequest request) {
        Flux<Person> people = repository.findAll();

        return ok().contentType(MediaType.APPLICATION_JSON).body(people, Person.class);
    }

    public Mono<ServerResponse> getPersonById(ServerRequest request) {
        String id = request.queryParam(ID_PARAM).get();
        Mono<Person> person = repository.findById(Long.valueOf(id));

        return ok().contentType(MediaType.APPLICATION_JSON).body(person, Person.class);
    }

    public Mono<ServerResponse> createPerson(ServerRequest request) {
        Person person = new Person(Long.valueOf(request.queryParam(ID_PARAM).get()), request.queryParam(NAME_PARAM).get());

        Mono<Person> savePerson = repository.save(person);

        return ok().contentType(MediaType.APPLICATION_JSON).body(savePerson, Person.class);
    }

    public Mono<ServerResponse> existsById(ServerRequest request) {
        String id = request.queryParam(ID_PARAM).get();
        Mono<Boolean> exists = repository.existsById(Long.valueOf(id));

        return ok().contentType(MediaType.APPLICATION_JSON).body(exists, Boolean.class);
    }
}
