package com.example.PostgreSQLDataBaseConnect.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.PostgreSQLDataBaseConnect.domain.dto.BookDto;
import com.example.PostgreSQLDataBaseConnect.domain.entities.BookEntity;
import com.example.PostgreSQLDataBaseConnect.mappers.Mapper;

@Component
public class BookMapper implements Mapper<BookEntity, BookDto> {

    private ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapTo(BookEntity book) {
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }

}
