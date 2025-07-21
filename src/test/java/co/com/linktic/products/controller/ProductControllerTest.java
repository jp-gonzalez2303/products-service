package co.com.linktic.products.controller;


import co.com.linktic.products.model.Product;
import co.com.linktic.products.service.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private IProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleProduct = Product.builder()
                .id(1L)
                .nombre("Producto Test")
                .precio(99.99)
                .descripcion("Test")
                .build();
    }

    @Test
    void testCreateProduct() {
        when(productService.createProduct(sampleProduct)).thenReturn(sampleProduct);

        ResponseEntity<Product> response = productController.createProduct(sampleProduct);

        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertEquals(sampleProduct, response.getBody());
        verify(productService, times(1)).createProduct(sampleProduct);
    }

    @Test
    void testGetProductByIdFound() {
        when(productService.findById(1L)).thenReturn((sampleProduct));

        ResponseEntity<Product> response = productController.getProductById(1L);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(sampleProduct, response.getBody());
        verify(productService, times(1)).findById(1L);
    }



    @Test
    void testGetAllProducts() {
        List<Product> products = Collections.singletonList(sampleProduct);
        when(productService.findAll()).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.getAllProducts();

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(products, response.getBody());
        verify(productService, times(1)).findAll();
    }
}