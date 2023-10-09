package order.integration.api.dtos.order;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderDto(
        @NotNull
        Long customerId,
        @NotNull
        List<OrderItemsDto> orderItems){
}
