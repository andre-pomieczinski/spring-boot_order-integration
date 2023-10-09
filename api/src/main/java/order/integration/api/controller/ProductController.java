package order.integration.api.controller;

import jakarta.validation.Valid;
import order.integration.api.dtos.product.ProductDetailDto;
import order.integration.api.dtos.product.ProductDto;
import order.integration.api.dtos.product.ProductListingDto;
import order.integration.api.dtos.product.ProductUpdateDto;
import order.integration.api.domain.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid ProductDto productDto, UriComponentsBuilder uriBuilder) {
        var productModel = productService.createProduct(productDto);
        var uri = uriBuilder.path("/product/{id}").buildAndExpand(productModel.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProductDetailDto(productModel));
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid ProductUpdateDto productUpdateDto) {
        var productModel = productService.updateProduct(productUpdateDto);
        return ResponseEntity.ok(new ProductDetailDto(productModel));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        productService.deactivateProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ProductListingDto>> getAllProducts(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        var page = productService.getAllProductPageList(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity getProduct(@PathVariable Long id) {
        var productModel = productService.getProductById(id);
        return ResponseEntity.ok(new ProductDetailDto(productModel));
    }

}
