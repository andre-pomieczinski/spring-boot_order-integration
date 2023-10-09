package order.integration.api.dtos.order;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderUpdateItemsDto(
        @NotNull
        Long orderId,
        @NotNull
        List<OrderItemsDto> orderItems) {
}
