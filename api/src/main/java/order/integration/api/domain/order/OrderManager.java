package order.integration.api.domain.order;

import order.integration.api.dtos.order.OrderMessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderManager {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrderAbandonmentQueue(OrderMessageDto orderMessageDto){
        String routingKey = "orders.v1.order-abandoned";
        rabbitTemplate.convertAndSend(routingKey, orderMessageDto);
    }

    public void sendOrderMessageToWebhook(OrderMessageDto orderMessageDto) {
        orderClient.sendOrderAbandoned(orderMessageDto);
    }

}
