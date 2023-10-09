package order.integration.api.dtos.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductOrderDto(
        @NotNull
        Long id,
        @NotBlank
        Integer quantity,
        @NotBlank
        String description,
        @NotNull
        Double unitValue,
        @NotNull
        Double weight) {
}
