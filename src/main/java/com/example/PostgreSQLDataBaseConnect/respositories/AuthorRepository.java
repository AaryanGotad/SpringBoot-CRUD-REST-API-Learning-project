package com.example.PostgreSQLDataBaseConnect.respositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.PostgreSQLDataBaseConnect.domain.entities.AuthorEntity;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {

    Iterable<AuthorEntity> findByAgeLessThan(int age);

    // Giving spring data jpa a hint for what query is required
    // This is HQL, not SQL
    // The hint query is saying "Select author with age greater than the first input parameter"
    @Query("SELECT a from AuthorEntity a where a.age > ?1")
    Iterable<AuthorEntity> findAuthorsWithAgeGreaterThan(int age);
}
