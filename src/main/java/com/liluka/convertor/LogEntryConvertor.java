package com.liluka.convertor;

import com.liluka.persistence.dto.LogEntryDTO;
import com.liluka.persistence.model.LogEntry;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class LogEntryConvertor {

    private final ModelMapper modelMapper;

    public LogEntryConvertor() {
        this.modelMapper = new ModelMapper();

        modelMapper.createTypeMap(LogEntry.class, LogEntryDTO.class)
                .addMapping(dao -> dao.getUser().getEmail(), LogEntryDTO::setUser);
    }

    public LogEntryDTO convertToDto(LogEntry dao) {
        return modelMapper.map(dao, LogEntryDTO.class);
    }
}
