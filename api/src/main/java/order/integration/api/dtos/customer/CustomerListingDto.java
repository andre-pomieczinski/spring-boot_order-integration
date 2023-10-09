package order.integration.api.dtos.customer;

import order.integration.api.model.CustomerModel;

public record CustomerListingDto(Long id, String name, String email, String phone, String cpf) {

    public CustomerListingDto(CustomerModel customerModel) {
        this(customerModel.getId(), customerModel.getName(), customerModel.getEmail(), customerModel.getPhone(), customerModel.getCpf());
    }
}
