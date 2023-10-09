package order.integration.api.dtos.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductUpdateDto(
        @NotNull
        Long id,
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotNull
        Double unitValue,
        @NotNull
        Double weight) {
}
