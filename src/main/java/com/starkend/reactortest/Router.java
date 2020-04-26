package com.starkend.reactortest;

import com.starkend.reactortest.person.PersonRouter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Log4j2
@Configuration
public class Router {

    private final PersonRouter personRouter;

    public Router(PersonRouter personRouter) {
        this.personRouter = personRouter;
    }

    @Bean
    RouterFunction<ServerResponse> route() { return personRouter.personRouter();}
}
