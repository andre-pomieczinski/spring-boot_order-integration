package order.integration.api.dtos.order;

import order.integration.api.model.OrderModel;
import order.integration.api.model.embeddable.OrderStatus;

import java.time.LocalDateTime;

public record OrderDetailDto(Long id, OrderStatus status, LocalDateTime createDate) {

    public OrderDetailDto(OrderModel orderModel) {
        this(orderModel.getId(), orderModel.getStatus(), orderModel.getCreateDate());
    }

}
