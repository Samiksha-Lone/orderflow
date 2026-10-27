package com.samiksha.orderflow;

import com.samiksha.orderflow.dto.ProductRequest;
import com.samiksha.orderflow.dto.ProductResponse;
import com.samiksha.orderflow.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponse createProduct(
            @Valid @RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping
    public Page<ProductResponse> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return productService.getAllProducts(page, size, sortBy, direction);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }


    @GetMapping("/search")
    public List<ProductResponse> searchProducts(
            @RequestParam String name) {

        return productService.searchProductsByName(name);
    }

    @GetMapping("/filter")
    public List<ProductResponse> filterProductsByPrice(
            @RequestParam(defaultValue = "0") double minPrice,
            @RequestParam double maxPrice) {

        if (minPrice < 0 || maxPrice < minPrice) {
            throw new IllegalArgumentException(
                    "Prices must be non-negative and maxPrice must be >= minPrice");
        }

        return productService.filterProductsByPrice(minPrice, maxPrice);
    }

    @GetMapping("/filter/max")
    public List<ProductResponse> filterProductsByMaxPrice(
            @RequestParam double maxPrice) {

        if (maxPrice < 0) {
            throw new IllegalArgumentException(
                    "maxPrice must be non-negative");
        }

        return productService.filterProductsByMaxPrice(maxPrice);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }
}