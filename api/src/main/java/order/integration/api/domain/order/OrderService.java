package order.integration.api.domain.order;

import order.integration.api.dtos.order.*;
import order.integration.api.model.OrderModel;
import order.integration.api.model.embeddable.OrderStatus;
import order.integration.api.repository.CustomerRepository;
import order.integration.api.infra.exceptions.ValidationException;
import order.integration.api.repository.OrderRepository;
import order.integration.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderManager orderManager;

    public OrderDetailDto createOrder(OrderDto orderDto) {
        if (!customerRepository.existsById(orderDto.customerId())) {
            throw new ValidationException("Customer ID does not exist!");
        }
        var customer = customerRepository.getReferenceById(orderDto.customerId());
        if (!customer.getActive()) {
            throw new RuntimeException("Customer is inactive!");
        }
        var order = new OrderModel(customer);
        addOrUpdateItemsToOrder(order, orderDto.orderItems());
        orderRepository.save(order);

        return new OrderDetailDto(order);
    }

    @CacheEvict(value="orders", key="#itemsUpdateDto.orderId")
    public OrderDetailDto updateOrderItems(OrderUpdateItemsDto itemsUpdateDto) {
        if (!orderRepository.existsById(itemsUpdateDto.orderId())) {
            throw new ValidationException("Order ID does not exist!");
        }
        var order = orderRepository.getReferenceById(itemsUpdateDto.orderId());
        addOrUpdateItemsToOrder(order, itemsUpdateDto.orderItems());
        orderRepository.save(order);
        return new OrderDetailDto(order);
    }

    @CacheEvict(value="orders", key="#order.id")
    public void addOrUpdateItemsToOrder(OrderModel order, List<OrderItemsDto> orderItemsDtos) {
        for (OrderItemsDto itemsDto: orderItemsDtos) {
            if (!productRepository.existsById(itemsDto.productId())) {
                throw new ValidationException("Product ID does not exist!");
            }

            var product = productRepository.getReferenceById(itemsDto.productId());
            if (product.getActive()) {
                order.addOrUpdateItem(product, itemsDto.quantity());
            } else {
                throw new RuntimeException("Product with id " + product.getId() + " is inactive!");
            }
        }
    }

    public void updateOrderStatusById(Long orderId, OrderStatus orderStatus) {
        var order = orderRepository.getReferenceById(orderId);
        updateOrderStatus(order, orderStatus);
    }

    @CacheEvict(value="orders", key="#orderId")
    public void updateOrderStatus(OrderModel order, OrderStatus orderStatus) {
        order.updateStatus(orderStatus);
        orderRepository.save(order);
    }

    @Cacheable(value = "orders", key = "#id")
    public OrderModel getOrderById(Long id) {
        System.out.println("Adicionando pedido na cache...");
        return orderRepository.findById(id).get();
    }

    public Page<OrderListingDto> getAllOrderPageList(Pageable pageable) {
        return orderRepository.findAllByStatus(OrderStatus.INCOMPLETE, pageable).map(OrderListingDto::new);
    }

    public void findOldIncompleteOrders(LocalDateTime localDateTime) {
        var orders = orderRepository.findAllByStatusAndLastUpdateTimeBefore(OrderStatus.INCOMPLETE, localDateTime);

        for (OrderModel order: orders) {
            updateOrderStatus(order, OrderStatus.ABANDONED);
            orderManager.sendOrderAbandonmentQueue(new OrderMessageDto(order));
        }
    }
}
