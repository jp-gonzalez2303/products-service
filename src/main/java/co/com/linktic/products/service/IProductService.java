package co.com.linktic.products.service;

import co.com.linktic.products.model.Product;

import java.util.List;

public interface IProductService {

    Product createProduct(Product product);

    Product findById(Long id);

    List<Product> findAll();
}
