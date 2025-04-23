package org.shoplite.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    private String description;

    @PositiveOrZero(message = "Цена должна быть положительной или 0")
    private BigDecimal price;

    @NotNull(message = "Id категории не может быть null")
    private Long categoryId;
}
