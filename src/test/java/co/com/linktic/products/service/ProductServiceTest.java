package co.com.linktic.products.service;

import co.com.linktic.products.entity.ProductEntity;
import co.com.linktic.products.exception.ProductAlreadyExistsException;
import co.com.linktic.products.exception.ProductNotFoundException;
import co.com.linktic.products.mapper.ProductMapper;
import co.com.linktic.products.mapper.ProductMapperImpl;
import co.com.linktic.products.model.Product;
import co.com.linktic.products.repository.IProductRepository;
import co.com.linktic.products.service.impl.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    @Mock
    private IProductRepository productRepository;

    private ProductMapper productMapper = new ProductMapperImpl();

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository, productMapper);
    }

    @Test
    void createProductSuccess() {
        Product input = Product.builder()
                .nombre("Manzana")
                .precio(2.5)
                .descripcion("Fruta roja")
                .build();

        ProductEntity savedEntity = ProductEntity.builder()
                .id(1L)
                .name("Manzana")
                .price(2.5)
                .description("Fruta roja")
                .build();

        when(productRepository.findByNameIgnoreCase("Manzana")).thenReturn(Optional.empty());
        when(productRepository.save(any())).thenReturn(savedEntity);

        Product result = productService.createProduct(input);

        assertNotNull(result);
        assertEquals("Manzana", result.getNombre());
        assertEquals(2.5, result.getPrecio());
    }

    @Test
    void createProductAlreadyExistsThrowsException() {
        Product input = Product.builder()
                .nombre("Banano")
                .precio(1.0)
                .build();

        when(productRepository.findByNameIgnoreCase("Banano"))
                .thenReturn(Optional.of(new ProductEntity()));

        assertThrows(ProductAlreadyExistsException.class, () -> productService.createProduct(input));
    }

    @Test
    void findByIdFound() {
        ProductEntity entity = ProductEntity.builder()
                .id(1L)
                .name("Kiwi")
                .price(3.0)
                .description("Fruta verde")
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(entity));

        Product result = productService.findById(1L);

        assertNotNull(result);
        assertEquals("Kiwi", result.getNombre());
    }

    @Test
    void findByIdNotFound() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        ProductNotFoundException notFoundException= assertThrows(ProductNotFoundException.class, () -> {
            productService.findById(2L);
        });
        assertEquals("El producto con id '2' no existe.", notFoundException.getMessage());

    }

    @Test
    void findAllReturnsList() {
        List<ProductEntity> entities = List.of(
                new ProductEntity(1L, "A", 1.0, "Desc A"),
                new ProductEntity(2L, "B", 2.0, "Desc B")
        );

        when(productRepository.findAll()).thenReturn(entities);

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getNombre());
    }
}
