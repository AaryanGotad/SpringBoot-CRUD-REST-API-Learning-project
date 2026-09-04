package com.example.PostgreSQLDataBaseConnect.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity // It simply labels this object as an entity to be used with Spring.JPA
@Table(name="authors") // which table this maps to in the DB
public class AuthorEntity {
    
    @Id // Which field in here is going to be our unique identifier
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq") // automatically generates the id for us
    private Long id;

    private String name;

    private Integer age;

}
