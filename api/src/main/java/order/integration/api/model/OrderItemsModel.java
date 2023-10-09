package order.integration.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "order_items")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class OrderItemsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel product;

    private int quantity;

    public OrderItemsModel(OrderModel orderModel, ProductModel product, int quantity) {
        this.order = orderModel;
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItemsModel{" +
                "id=" + id +
                ", orderId=" + order.getId() +
                ", productId=" + product.getId() +
                ", quantity=" + quantity +
                '}';
    }

    public void updateItem(Integer quantity) {
        this.quantity = quantity;
    }
}
