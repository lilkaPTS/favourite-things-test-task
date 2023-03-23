package com.liluka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@AllArgsConstructor
public class FavouriteThingsEntryDTO {

    String dish;
    @Pattern(regexp = "^#.{6}$", message = "Цвет передан в неправильном формате. Пример: #FF5733")
    String color;
    String song;
    //TODO форматировать даты
    Date date;
    Integer number;
}
