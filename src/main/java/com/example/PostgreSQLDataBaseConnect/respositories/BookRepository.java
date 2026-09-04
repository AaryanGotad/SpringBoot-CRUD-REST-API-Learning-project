package com.example.PostgreSQLDataBaseConnect.respositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.PostgreSQLDataBaseConnect.domain.entities.BookEntity;

// @Repository                                         // ( dealing with ), ( how to identify it [type of the ID] ) 
public interface BookRepository extends CrudRepository<BookEntity, String>,
    PagingAndSortingRepository<BookEntity, String> {
}
