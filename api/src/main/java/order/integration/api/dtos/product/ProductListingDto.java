package order.integration.api.dtos.product;

import order.integration.api.model.ProductModel;

public record ProductListingDto(Long id, String name, String description, Double unitValue, Double weight) {

    public ProductListingDto(ProductModel productModel) {
        this(productModel.getId(), productModel.getName(), productModel.getDescription(), productModel.getUnitValue(), productModel.getWeight());
    }

}
