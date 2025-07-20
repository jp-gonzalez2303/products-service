package co.com.linktic.products.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("El producto con id '" + id + "' no existe.");
    }
}