package order.integration.api.controller;

import jakarta.validation.Valid;
import order.integration.api.domain.customer.CustomerService;
import order.integration.api.dtos.customer.CustomerListingDto;
import order.integration.api.dtos.customer.CustomerUpdateDto;
import order.integration.api.dtos.customer.CustomerDetailDto;
import order.integration.api.dtos.customer.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid CustomerDto customerDto, UriComponentsBuilder uriBuilder) {
        var customerModel = customerService.create(customerDto);
        var uri = uriBuilder.path("/customer/{id}").buildAndExpand(customerModel.getId()).toUri();
        return ResponseEntity.created(uri).body(new CustomerDetailDto(customerModel));
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid CustomerUpdateDto customerUpdateDto) {
        var customerModel = customerService.update(customerUpdateDto);
        return ResponseEntity.ok(new CustomerDetailDto(customerModel));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        customerService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<CustomerListingDto>> getAllCustomers(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        var page = customerService.getAllCustomerPageList(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCustomer(@PathVariable Long id) {
        var customerModel = customerService.getCustomerById(id);
        return ResponseEntity.ok(new CustomerDetailDto(customerModel));
    }

}
