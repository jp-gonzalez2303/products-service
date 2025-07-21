package co.com.linktic.products.exception;

public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException(String name) {
        super("El producto con nombre '" + name + "' ya existe.");
    }
}