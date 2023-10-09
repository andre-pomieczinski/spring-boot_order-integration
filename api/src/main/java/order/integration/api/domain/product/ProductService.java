package order.integration.api.domain.product;

import order.integration.api.dtos.product.ProductDto;
import order.integration.api.dtos.product.ProductListingDto;
import order.integration.api.dtos.product.ProductUpdateDto;
import order.integration.api.model.ProductModel;
import order.integration.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductModel createProduct(ProductDto productDto) {
        var productModel = new ProductModel(productDto);
        return productRepository.save(productModel);
    }

    @CacheEvict(value="products", key="#productUpdateDto.id")
    public ProductModel updateProduct(ProductUpdateDto productUpdateDto) {
        var productModel = getProductById(productUpdateDto.id());
        productModel.updateInfos(productUpdateDto);
        return productRepository.save(productModel);
    }

    public void deactivateProduct(Long id) {
        var productModel = productRepository.getReferenceById(id);
        productModel.delete();
        productRepository.save(productModel);
    }

    @Cacheable(value = "products", key = "#id")
    public ProductModel getProductById(Long id) {
        System.out.println("Adicionando produto na cache...");
        return productRepository.findById(id).get();
    }

    public Page<ProductListingDto> getAllProductPageList(Pageable pageable) {
        return productRepository.findAllByActiveTrue(pageable).map(ProductListingDto::new);
    }

}
