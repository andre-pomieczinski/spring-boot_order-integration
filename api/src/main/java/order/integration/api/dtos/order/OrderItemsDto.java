package order.integration.api.dtos.order;

import jakarta.validation.constraints.NotNull;

public record OrderItemsDto(
        @NotNull
        Long productId,
        @NotNull
        Integer quantity) {
}
