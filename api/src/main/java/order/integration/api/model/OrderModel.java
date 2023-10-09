package order.integration.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import order.integration.api.model.embeddable.OrderStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Table(name = "orders")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class OrderModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerModel customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemsModel> orderItems;

    public OrderModel(CustomerModel customer) {
        this.status = OrderStatus.INCOMPLETE;
        this.createDate = LocalDateTime.now();
        this.lastUpdateTime = LocalDateTime.now();
        this.customer = customer;
    }

    @PreUpdate
    public void preUpdate() {
        updateLastUpdateTime(LocalDateTime.now());
    }

    public void updateLastUpdateTime(LocalDateTime localDateTime){
        this.lastUpdateTime = localDateTime;
    }

    public void addOrUpdateItem(ProductModel product, int quantity) {
        Optional<OrderItemsModel> orderItem = this.getOrderItems()
                .stream()
                .filter(items -> product.equals(items.getProduct()))
                .findFirst();

        if (!orderItem.isEmpty()) {
            orderItem.get().updateItem(quantity);
        } else {
            OrderItemsModel newOrderItem = new OrderItemsModel(this, product, quantity);
            getOrderItems().add(newOrderItem);
        }
        updateLastUpdateTime(LocalDateTime.now());
    }

    public List<OrderItemsModel> getOrderItems() {
        if (this.orderItems == null) {
            this.orderItems = new ArrayList<>();
        }
        return this.orderItems;
    }

    public void updateStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }
}
