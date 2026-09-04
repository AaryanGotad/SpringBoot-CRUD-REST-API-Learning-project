package com.example.PostgreSQLDataBaseConnect.services;

import java.util.List;
import java.util.Optional;

import com.example.PostgreSQLDataBaseConnect.domain.entities.AuthorEntity;

public interface AuthorService {
    AuthorEntity save(AuthorEntity author);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(Long id);

    boolean isExists(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);

    void delete(Long id);
}
