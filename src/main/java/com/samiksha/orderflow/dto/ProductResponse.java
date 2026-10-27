package com.samiksha.orderflow.dto;

public class ProductResponse {

    private Long id;
    private String name;
    private double price;
    private boolean inStock;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String name, double price, boolean inStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isInStock() {
        return inStock;
    }
}