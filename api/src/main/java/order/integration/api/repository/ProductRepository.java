package order.integration.api.repository;

import order.integration.api.model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    Page<ProductModel> findAllByActiveTrue(Pageable pageable);
}
