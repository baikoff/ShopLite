package org.shoplite.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
    @NotBlank(message = "Не может быть пустым")
    private String name;

    @NotBlank(message = "е может быть пустым")
    @Email(message = "Имеил должен быть валидным")
    private String email;
}
