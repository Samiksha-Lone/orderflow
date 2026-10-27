
package com.samiksha.orderflow.repository;

import com.samiksha.orderflow.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByPriceLessThanEqual(double maxPrice);
}
