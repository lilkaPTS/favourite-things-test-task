package com.liluka.config;

import com.liluka.dto.LogEntryDTO;
import com.liluka.model.LogEntry;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(LogEntry.class, LogEntryDTO.class)
                .addMapping(dao -> dao.getUser().getEmail(), LogEntryDTO::setUser);
        return modelMapper;
    }
}
