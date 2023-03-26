package com.liluka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
public class RegistrationUserDTO {

    @NotBlank(message = "ФИО не может быть пустым")
    private String name;

    @NotNull(message = "Дата рождения не может быть пустой")
    @Past(message = "Дата должна быть в прошлом")
    private LocalDate dob;

    @NotBlank(message = "E-mail не может быть пустым")
    @Email(message = "Введенное значение не является email")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @NotBlank(message = "Повторный пароль не может быть пустым")
    private String confirmPassword;

    @AssertTrue(message = "Пароли не совпадают")
    private boolean isPasswordConfirmed() {
        return Objects.equals(password, confirmPassword);
    }
}
