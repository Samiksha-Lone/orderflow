package com.samiksha.orderflow.service;

import com.samiksha.orderflow.Product;
import com.samiksha.orderflow.dto.ProductRequest;
import com.samiksha.orderflow.dto.ProductResponse;
import com.samiksha.orderflow.exception.ProductNotFoundException;
import com.samiksha.orderflow.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product(
                request.getName(),
                request.getPrice(),
                request.isInStock()
        );

        Product savedProduct = productRepository.save(product);
        return toResponse(savedProduct);
    }

    public Page<ProductResponse> getAllProducts(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public ProductResponse getProductById(Long id) {
        Product product = findProduct(id);
        return toResponse(product);
    }


    public List<ProductResponse> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ProductResponse> filterProductsByPrice(
            double minPrice, double maxPrice) {

        return productRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ProductResponse> filterProductsByMaxPrice(double maxPrice) {
        return productRepository.findByPriceLessThanEqual(maxPrice)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = findProduct(id);

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setInStock(request.isInStock());

        Product updatedProduct = productRepository.save(product);
        return toResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = findProduct(id);
        productRepository.delete(product);
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.isInStock()
        );
    }
}