package order.integration.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import order.integration.api.dtos.product.ProductDto;
import order.integration.api.dtos.product.ProductUpdateDto;

import java.io.Serializable;

@Table(name = "products")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double unitValue;

    private Double weight;

    private Boolean active;

    public ProductModel(ProductDto productDto) {
        this.active = true;
        this.name = productDto.name();
        this.description = productDto.description();
        this.unitValue = productDto.unitValue();
        this.weight = productDto.weight();
    }

    public void updateInfos(ProductUpdateDto productUpdateDto) {
        if (productUpdateDto.name() != null)
            this.name = productUpdateDto.name();

        if (productUpdateDto.description() != null)
            this.description = productUpdateDto.description();

        if (productUpdateDto.unitValue() != null)
            this.unitValue = productUpdateDto.unitValue();

        if (productUpdateDto.weight() != null)
            this.weight = productUpdateDto.weight();
    }

    public void delete() {
        this.active = false;
    }
}
