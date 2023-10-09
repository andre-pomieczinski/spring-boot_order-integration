package order.integration.api.domain.rabbitMQ;

import order.integration.api.dtos.order.OrderMessageDto;
import order.integration.api.domain.order.OrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitListener {
    @Autowired
    private OrderManager orderManager;

    @org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "orders.v1.order-abandoned")
    public void onOrderAbandoned(OrderMessageDto orderMessageDto) {
        orderManager.sendOrderMessageToWebhook(orderMessageDto);
    }
}
