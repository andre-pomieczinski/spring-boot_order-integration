package order.integration.api.domain.scheduler;

import order.integration.api.domain.order.OrderManager;
import order.integration.api.domain.order.OrderService;
import order.integration.api.model.OrderModel;
import order.integration.api.model.embeddable.OrderStatus;
import order.integration.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Component
@EnableScheduling
public class CheckAbandonedOrdersScheduler {
    private static final Logger logger = LoggerFactory.getLogger(CheckAbandonedOrdersScheduler.class);
    private static final String time_zone = "America/Sao_Paulo";

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 * * * * *", zone = time_zone)
    public void checkAbandonedOrders() {
        logger.info("Checking incomplete orders for more than 48 hours...");

        LocalDateTime localDateTime = LocalDateTime.now().plusHours(-48);
        orderService.findOldIncompleteOrders(localDateTime);

        logger.info("Schedule completed!");
    }
}
