package order.integration.api.controller;

import jakarta.validation.Valid;
import order.integration.api.domain.order.OrderService;
import order.integration.api.dtos.order.OrderDetailDto;
import order.integration.api.dtos.order.OrderDto;
import order.integration.api.dtos.order.OrderListingDto;
import order.integration.api.dtos.order.OrderUpdateItemsDto;
import order.integration.api.model.embeddable.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid OrderDto orderDto) {
        var orderDetailDto = orderService.createOrder(orderDto);
        return ResponseEntity.ok(orderDetailDto);
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateOrderItems(@RequestBody @Valid OrderUpdateItemsDto itemsUpdateDto) {
        var orderDetailDto = orderService.updateOrderItems(itemsUpdateDto);
        return ResponseEntity.ok(orderDetailDto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity cancel(@PathVariable Long id) {
        orderService.updateOrderStatusById(id, OrderStatus.CANCELED);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<OrderListingDto>> getAllOrders(@PageableDefault(size = 10, sort = {"createDate"}) Pageable pageable) {
        var page = orderService.getAllOrderPageList(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOrder(@PathVariable Long id) {
        var order = orderService.getOrderById(id);
        return ResponseEntity.ok(new OrderDetailDto(order));
    }
}
