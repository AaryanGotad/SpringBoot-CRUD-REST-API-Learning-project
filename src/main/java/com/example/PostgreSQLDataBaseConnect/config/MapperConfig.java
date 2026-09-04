package com.example.PostgreSQLDataBaseConnect.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
// import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    // private final ModelMapper modelMapper;

    // MapperConfig(ModelMapper modelMapper) {
    //     this.modelMapper = modelMapper;
    // }

    @Bean
    public ModelMapper modelMapper () {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}
