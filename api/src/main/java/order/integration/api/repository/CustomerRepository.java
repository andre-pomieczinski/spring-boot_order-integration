package order.integration.api.repository;

import order.integration.api.model.CustomerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
    Page<CustomerModel> findAllByActiveTrue(Pageable pageable);
}
