package com.trendyol.product.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.trendyol.product.Domain.Product;
import com.trendyol.product.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public ProductService(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    public Product createProduct(Product product) {
        product.setId(UUID.randomUUID().toString());
        return productRepository.createProduct(product);
    }

    public Optional<Product> findProductById(String id) {
        return productRepository.findProductById(id);
    }

    public void deleteProduct(String id) {
        productRepository.deleteProduct(id);
    }

    public void update(String id, Product product) {
        try {
            productRepository.updateProduct(id, product);
        } catch (Exception e) {
            throw new IllegalArgumentException(); // check again
        }
    }

    public void save(Product product) {
        try {
            productRepository.saveProduct(product);
        } catch (Exception e) {
            throw new IllegalArgumentException(); // check again
        }
    }


}
