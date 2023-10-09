package order.integration.api.dtos.order;

import order.integration.api.model.OrderModel;
import order.integration.api.model.embeddable.OrderStatus;

import java.time.LocalDateTime;

public record OrderListingDto(Long id, OrderStatus status, LocalDateTime createDate) {

    public OrderListingDto(OrderModel orderModel) {
        this(orderModel.getId(), orderModel.getStatus(), orderModel.getCreateDate());
    }

}
