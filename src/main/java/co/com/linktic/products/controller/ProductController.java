package co.com.linktic.products.controller;

import co.com.linktic.products.model.Product;
import co.com.linktic.products.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final IProductService productService;

    /**
     * Creates a new product and returns the created product in the response.
     *
     * @param product the product object to be created, validated for correctness.
     * @return a ResponseEntity containing the created product and an HTTP status of 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        return ResponseEntity.status(201).body(productService.createProduct(product));
    }

    /**
     * Retrieves a product by its unique identifier.
     *
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    /**
     * Retrieves a list of all products.
     *
     * @return a ResponseEntity containing a list of all products and an HTTP status of 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }
}