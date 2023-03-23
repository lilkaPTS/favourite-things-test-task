package com.liluka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotBlank(message = "E-mail не может быть пустым")
    @Email(message = "Введенное значение не является email")
    private String email;
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
