package com.ws101.balanquit_balansag.ecommerceapi.service;

import com.ws101.balanquit_balansag.ecommerceapi.model.Product;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    // In-memory storage using ArrayList
    private final List<Product> productList = new ArrayList<>();
    
    // Auto-increment ID generator
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    // Constructor - adds sample products when service is created
    public ProductService() {
        initSampleProducts();
    }
    
    // Initialize with 10 sample products
    private void initSampleProducts() {
        productList.add(new Product(idGenerator.getAndIncrement(), "Laptop", "High-performance laptop", 1200.00, "Electronics", 10, "https://example.com/laptop.jpg"));
        productList.add(new Product(idGenerator.getAndIncrement(), "Smartphone", "Latest model smartphone", 800.00, "Electronics", 25, "https://example.com/phone.jpg"));
        productList.add(new Product(idGenerator.getAndIncrement(), "Headphones", "Noise-cancelling headphones", 150.00, "Audio", 50, "https://example.com/headphones.jpg"));
        productList.add(new Product(idGenerator.getAndIncrement(), "Keyboard", "Mechanical keyboard", 100.00, "Accessories", 30, "https://example.com/keyboard.jpg"));
        productList.add(new Product(idGenerator.getAndIncrement(), "Mouse", "Wireless mouse", 40.00, "Accessories", 45, "https://example.com/mouse.jpg"));
        productList.add(new Product(idGenerator.getAndIncrement(), "Monitor", "4K Ultra HD Monitor", 350.00, "Electronics", 15, "https://example.com/monitor.jpg"));
        productList.add(new Product(idGenerator.getAndIncrement(), "Backpack", "Waterproof laptop backpack", 60.00, "Bags", 20, "https://example.com/backpack.jpg"));
        productList.add(new Product(idGenerator.getAndIncrement(), "USB Cable", "USB-C fast charging cable", 15.00, "Accessories", 100, "https://example.com/cable.jpg"));
        productList.add(new Product(idGenerator.getAndIncrement(), "Desk Lamp", "LED adjustable desk lamp", 35.00, "Home", 12, "https://example.com/lamp.jpg"));
        productList.add(new Product(idGenerator.getAndIncrement(), "Coffee Mug", "Ceramic coffee mug", 12.00, "Home", 8, "https://example.com/mug.jpg"));
    }
    
    // Get all products
    public List<Product> getAllProducts() {
        return productList;
    }
    
    // Get product by ID
    public Optional<Product> getProductById(Long id) {
        return productList.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }
    
    // Create new product
    public Product createProduct(Product product) {
        product.setId(idGenerator.getAndIncrement());
        productList.add(product);
        return product;
    }
    
    // Update existing product (PUT - full replace)
    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        return getProductById(id).map(existingProduct -> {
            updatedProduct.setId(id);
            productList.set(productList.indexOf(existingProduct), updatedProduct);
            return updatedProduct;
        });
    }
    
    // Partially update product (PATCH)
    public Optional<Product> patchProduct(Long id, Product patchData) {
        return getProductById(id).map(existingProduct -> {
            if (patchData.getName() != null) existingProduct.setName(patchData.getName());
            if (patchData.getDescription() != null) existingProduct.setDescription(patchData.getDescription());
            if (patchData.getPrice() != null) existingProduct.setPrice(patchData.getPrice());
            if (patchData.getCategory() != null) existingProduct.setCategory(patchData.getCategory());
            if (patchData.getStockQuantity() != null) existingProduct.setStockQuantity(patchData.getStockQuantity());
            if (patchData.getImageUrl() != null) existingProduct.setImageUrl(patchData.getImageUrl());
            return existingProduct;
        });
    }
    
    // Delete product
    public boolean deleteProduct(Long id) {
        return productList.removeIf(product -> product.getId().equals(id));
    }
    
    // Filter products by category
    public List<Product> filterByCategory(String category) {
        return productList.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .toList();
    }
    
    // Filter products by price range
    public List<Product> filterByPriceRange(Double minPrice, Double maxPrice) {
        return productList.stream()
                .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                .toList();
    }
    
    // Filter products by name (contains keyword)
    public List<Product> filterByName(String keyword) {
        return productList.stream()
                .filter(product -> product.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }
}
