package order.integration.api.domain.order;

import order.integration.api.domain.scheduler.CheckAbandonedOrdersScheduler;
import order.integration.api.dtos.order.OrderMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class OrderClient {
    private static final Logger logger = LoggerFactory.getLogger(OrderClient.class);

    private final String webhookUrl = "https://webhook.site/3514a4fe-fac4-45df-b223-1f573805f2ab";

    public void sendOrderAbandoned(OrderMessageDto orderMessageDto) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(webhookUrl, orderMessageDto, Map.class);
            if (HttpStatus.OK != response.getStatusCode()) {
                throw new Exception("Request submission for abandoned orders failed.");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
