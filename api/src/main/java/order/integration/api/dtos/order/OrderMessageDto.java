package order.integration.api.dtos.order;

import order.integration.api.model.OrderModel;
import order.integration.api.model.embeddable.OrderStatus;

public record OrderMessageDto(Long id, OrderStatus status, String lastUpdateTime) {

    public OrderMessageDto(OrderModel orderModel) {
        this(orderModel.getId(), orderModel.getStatus(), orderModel.getLastUpdateTime().toString());
    }

}
