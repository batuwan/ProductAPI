package com.trendyol.product.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.trendyol.product.Domain.Product;
import com.trendyol.product.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product){
        Product newProduct = productService.createProduct(product);
        return ResponseEntity.created(URI.create(newProduct.getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> findProductById(@PathVariable ("id") String id) {
        Optional<Product> product = productService.findProductById(id);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable ("id") String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping (path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Product> updateProduct(@PathVariable ("id") String productId, @RequestBody JsonPatch patch) throws JsonPatchException, JsonProcessingException {

        Product product = productService.update(productId, patch);
        return ResponseEntity.ok(product);
    }
}
