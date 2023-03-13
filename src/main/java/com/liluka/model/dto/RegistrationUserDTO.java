package com.liluka.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.validation.constraints.*;
import java.util.Objects;

@Data
@AllArgsConstructor
@Log4j2
public class RegistrationUserDTO {

    @NotBlank(message = "ФИО не может быть пустым")
    private String name;

    //TODO -->> make dob validation
    @NotBlank(message = "Дата рождения не может быть пустой")
    private String dob;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Введенное значение не является email")
    private String email;

    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;

    @NotBlank(message = "Повторный пароль не должен быть пустым")
    private String confirmPassword;

    @AssertTrue(message = "Пароли не совпадают")
    private boolean isPasswordConfirmed() {
        return Objects.equals(password, confirmPassword);
    }
}
