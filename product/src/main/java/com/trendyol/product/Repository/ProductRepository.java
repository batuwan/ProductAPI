package com.trendyol.product.Repository;


import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.trendyol.product.Domain.Product;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductRepository {
    private final Cluster couchbaseCluster;
    private final Collection productCollection;

    public ProductRepository(Cluster couchbaseCluster, Collection productCollection) {
        this.couchbaseCluster = couchbaseCluster;
        this.productCollection = productCollection;
    }

    public Product createProduct(Product product) {

        productCollection.insert(product.getId(), product);
        return product;
    }

    public Optional<Product> findProductById(String id) {

        try {
            GetResult getResult = productCollection.get(id);
            Product product = getResult.contentAs(Product.class);
            return Optional.of(product);

        } catch (DocumentNotFoundException exception) {
            return Optional.empty();
        }

    }

    public void deleteProduct(String id) {
        productCollection.remove(id);
    }

    public void updateProduct(String id, Product product) {
        productCollection.replace(id, product);
    }
}

