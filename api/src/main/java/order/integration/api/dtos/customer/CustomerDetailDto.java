package order.integration.api.dtos.customer;

import order.integration.api.model.embeddable.Address;
import order.integration.api.model.CustomerModel;

public record CustomerDetailDto(Long id, String name, String email, String phone, String cpf, Address address) {

    public CustomerDetailDto(CustomerModel customerModel) {
        this(customerModel.getId(), customerModel.getName(), customerModel.getEmail(), customerModel.getPhone(), customerModel.getCpf(), customerModel.getAddress());
    }

}



