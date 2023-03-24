package com.liluka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class FavouriteThingsEntryDTO {

    private String dish;
    @Pattern(regexp = "^#.{6}$", message = "Цвет передан в неправильном формате. Пример: #FF5733")
    private String color;
    private String song;
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01]).(0[1-9]|1[012])$", message = "Дата передана в неправильном формате. Пример: 20.07")
    private String date;
    private Integer number;
}
