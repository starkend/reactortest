package com.starkend.reactortest.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Log4j2
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Person {

    @Id
    private Long id;

    @NonNull
    private String name;
}
