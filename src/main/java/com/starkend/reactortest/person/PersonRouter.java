package com.starkend.reactortest.person;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Log4j2
@Data
@Component
public class PersonRouter {

    private final PersonHandler personHandler;

    public PersonRouter(PersonHandler personHandler) {
        this.personHandler = personHandler;
    }

    public RouterFunction<ServerResponse> personRouter() {
        return RouterFunctions.route(GET("/people").and(accept(MediaType.APPLICATION_JSON)), personHandler::listPeople)
                .andRoute(GET("/createPerson").and(accept(MediaType.APPLICATION_JSON)), personHandler::createPerson);
    }
}
