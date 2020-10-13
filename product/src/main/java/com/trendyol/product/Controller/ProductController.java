package com.trendyol.product.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.trendyol.product.Domain.Product;
import com.trendyol.product.Service.ProductService;
import com.trendyol.product.Service.RestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/v1/products")
public class ProductController {
    private final ProductService productService;
    private final RestService restService;

    public ProductController(ProductService productService, RestService restService) {
        this.productService = productService;
        this.restService = restService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws URISyntaxException {
        Product newProduct = productService.createProduct(product);
        ResponseEntity<String> response = restService.createStock(product);
        if(response.getStatusCodeValue() == 201){
            return ResponseEntity.created(URI.create(newProduct.getId())).build();
        }
        return null;
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

    @PutMapping ("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable ("id") String productId, @RequestBody Product product){
        productService.update(productId, product);
        return ResponseEntity.ok(product);
    }

    @PatchMapping ("/{id}")
    public ResponseEntity<Void> updateProductCategory(@RequestParam(name = "category") String userId, @PathVariable String id){
        return ResponseEntity.ok().build();
    }
}
