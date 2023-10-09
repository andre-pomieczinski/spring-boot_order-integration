package order.integration.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import order.integration.api.dtos.customer.CustomerDto;
import order.integration.api.dtos.customer.CustomerUpdateDto;
import order.integration.api.model.embeddable.Address;

import java.io.Serializable;

@Table(name = "customers")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CustomerModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String cpf;

    @Embedded
    private Address address;

    private Boolean active;

    public CustomerModel(CustomerDto customerDto) {
        this.active = true;
        this.name = customerDto.name();
        this.email = customerDto.email();
        this.phone = customerDto.phone();
        this.cpf = customerDto.cpf();
        this.address = new Address(customerDto.address());
    }

    public void updateInfos(CustomerUpdateDto customerUpdateDto) {
        if (customerUpdateDto.name() != null)
            this.name = customerUpdateDto.name();

        if (customerUpdateDto.email() != null)
            this.email = customerUpdateDto.email();

        if (customerUpdateDto.phone() != null)
            this.phone = customerUpdateDto.phone();

        if (customerUpdateDto.address() != null)
            this.address.updateInfos(customerUpdateDto.address());
    }

    public void delete() {
        this.active = false;
    }
}
