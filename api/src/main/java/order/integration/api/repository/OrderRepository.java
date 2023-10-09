package order.integration.api.repository;

import order.integration.api.model.OrderModel;
import order.integration.api.model.embeddable.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    Page<OrderModel> findAllByStatus(OrderStatus status, Pageable pageable);

    List<OrderModel> findAllByStatusAndLastUpdateTimeBefore(OrderStatus status, LocalDateTime dateTime);
}
