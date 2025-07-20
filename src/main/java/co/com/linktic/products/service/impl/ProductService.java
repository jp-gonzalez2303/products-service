package co.com.linktic.products.service.impl;

import co.com.linktic.products.entity.ProductEntity;
import co.com.linktic.products.exception.ProductAlreadyExistsException;
import co.com.linktic.products.exception.ProductNotFoundException;
import co.com.linktic.products.mapper.ProductMapper;
import co.com.linktic.products.model.Product;
import co.com.linktic.products.repository.IProductRepository;
import co.com.linktic.products.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ProductMapper productMapper;

    /**
     * Creates a new product if a product with the same name does not already exist.
     *
     * @param product the product information to be created
     * @return the created product as a data transfer object (DTO)
     * @throws ProductAlreadyExistsException if a product with the same name already exists
     */
    @Override
    public Product createProduct(Product product) {
        log.info("Creating product {}", product);

        if (productRepository.findByNameIgnoreCase(product.getNombre()).isPresent()) {
            log.warn("Product with name {} already exists", product.getNombre());
            throw new ProductAlreadyExistsException(product.getNombre());
        }
        log.info("Product  available to save{}", product);
        ProductEntity savedProduct = productRepository.save(productMapper.toEntity(product));
        return productMapper.toDTO(savedProduct);
    }

    /**
     * Finds a product by its unique identifier and returns it as a data transfer object (DTO).
     *
     * @param id the unique identifier of the product to be retrieved
     * @return the product data transfer object (DTO) corresponding to the given identifier
     * @throws ProductNotFoundException if no product is found with the given identifier
     */
    @Override
    public Product findById(Long id) {
        log.info("Finding product by id {}", id);
        return productRepository.findById(id)
                .map(productMapper::toDTO)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * Retrieves all products from the repository, maps them to data transfer objects (DTOs),
     * and returns them as a list.
     *
     * @return a list of products as data transfer objects (DTOs)
     */
    @Override
    public List<Product> findAll() {
        log.info("Finding all products");
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .toList();
    }

}