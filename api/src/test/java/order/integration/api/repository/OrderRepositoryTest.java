package order.integration.api.repository;

import order.integration.api.dtos.address.AddressDto;
import order.integration.api.dtos.customer.CustomerDto;
import order.integration.api.dtos.product.ProductDto;
import order.integration.api.model.CustomerModel;
import order.integration.api.model.OrderModel;
import order.integration.api.model.ProductModel;
import order.integration.api.model.embeddable.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("hml")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("return null when there is no incomplete order for more than 48 hours")
    void findAllByStatusAndLastUpdateTimeBefore_scene01() {
        var custumer = createCustomer("customer", "customer@customer", "(54) 991684315", "03293671338");
        var product = createProduct("product", "product description", 19.99, 5.56);
        createOrder(custumer, product);
        var ordersIncomplete = orderRepository.findAllByStatusAndLastUpdateTimeBefore(OrderStatus.INCOMPLETE, LocalDateTime.now().plusHours(-48));
        assertThat(ordersIncomplete).isEmpty();
    }

    @Test
    @DisplayName("return order when there is incomplete order for more than 48 hours")
    void findAllByStatusAndLastUpdateTimeBefore_scene02() {
        var custumer = createCustomer("customer", "customer@customer", "(54) 991684315", "03293671338");
        var product = createProduct("product", "product description", 19.99, 5.56);
        OrderModel order = createOrderAndUpdateLastDateTime(custumer, product);

        var ordersIncomplete = orderRepository.findAllByStatusAndLastUpdateTimeBefore(OrderStatus.INCOMPLETE, LocalDateTime.now().plusHours(-48));
        assertThat(ordersIncomplete).isNotEmpty();
    }

    private OrderModel createOrder(CustomerModel customer, ProductModel product) {
        var order = new OrderModel(customer);
        order.addOrUpdateItem(product, 5);;
        return em.persist(order);
    }

    private OrderModel createOrderAndUpdateLastDateTime(CustomerModel customer, ProductModel product) {
        var order = new OrderModel(customer);
        order.addOrUpdateItem(product, 5);
        order.updateLastUpdateTime(LocalDateTime.now().plusHours(-50));
        return em.persist(order);
    }

    private CustomerModel createCustomer(String name, String email, String phone, String cpf) {
        var customer = new CustomerModel(customerDto(name, email, phone, cpf));
        em.persist(customer);
        return customer;
    }

    private CustomerDto customerDto(String name, String email, String phone, String cpf) {
        return new CustomerDto(
                name,
                email,
                phone,
                cpf,
                addressDto()
        );
    }

    private AddressDto addressDto() {
        return new AddressDto(
                "Erebango",
                "RS",
                "Centro",
                "Rua Geraldo Miguel",
                "345",
                "Casa",
                "99805654"
        );
    }

    private ProductModel createProduct(String name, String description, Double unitValue, Double weight) {
        var product = new ProductModel(productDto(name, description, unitValue, weight));
        em.persist(product);
        return product;
    }

    private ProductDto productDto(String name, String description, Double unitValue, Double weight) {
        return new ProductDto(
                name,
                description,
                unitValue,
                weight
        );
    }


}