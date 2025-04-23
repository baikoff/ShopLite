package org.shoplite.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class BasketDTO {
    @NotNull(message = "Id пользователя не может быть null")
    private Long userId;

    @NotEmpty(message = "Id продукта не может быть пустым")
    private List<Long> productIds;
}
