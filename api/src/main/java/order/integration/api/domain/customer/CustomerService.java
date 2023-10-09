package order.integration.api.domain.customer;

import order.integration.api.dtos.customer.CustomerDto;
import order.integration.api.dtos.customer.CustomerListingDto;
import order.integration.api.dtos.customer.CustomerUpdateDto;
import order.integration.api.model.CustomerModel;
import order.integration.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerModel create(CustomerDto customerDto) {
        var customerModel = new CustomerModel(customerDto);
        return customerRepository.save(customerModel);
    }

    @CacheEvict(value="customers", key="#customerUpdateDto.id")
    public CustomerModel update(CustomerUpdateDto customerUpdateDto) {
        var customerModel = getCustomerById(customerUpdateDto.id());
        customerModel.updateInfos(customerUpdateDto);
        return customerRepository.save(customerModel);
    }

    public void deactivate(Long id) {
        var customerModel = customerRepository.getReferenceById(id);
        customerModel.delete();
        customerRepository.save(customerModel);
    }

    @Cacheable(value = "customers", key = "#id")
    public CustomerModel getCustomerById(Long id) {
        System.out.println("Adicionando cliente na cache...");
        return customerRepository.findById(id).get();
    }

    public Page<CustomerListingDto> getAllCustomerPageList(Pageable pageable) {
        return customerRepository.findAllByActiveTrue(pageable).map(CustomerListingDto::new);
    }

}
