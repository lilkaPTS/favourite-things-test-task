package com.liluka.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogEntryDTO {
    private Date date;
    private String user;
    private String endpoint;
    private HttpStatus httpStatus;
}
