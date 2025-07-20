package co.com.linktic.products.exception;


import co.com.linktic.products.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles the {@link ProductAlreadyExistsException} thrown when attempting to create or add a product
     * that already exists in the system.
     *
     * @param ex the exception thrown when a product already exists
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with the error code "PRODUCT_EXISTS"
     *         and an appropriate error message, along with an HTTP status of 409 Conflict
     */
    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleProductExists(ProductAlreadyExistsException ex) {
        log.error("Product already exists: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("PRODUCT_EXISTS", ex.getMessage()));
    }

    /**
     * Handles validation errors thrown when a method argument annotated with validation constraints fails.
     * Extracts field-specific error messages and aggregates them into a single response.
     *
     * @param ex the {@link MethodArgumentNotValidException} containing validation errors
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with the error code "VALIDATION_ERROR"
     *         and a concatenated string of validation error messages, along with an HTTP status of 400 Bad Request
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage());
        String error = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("Datos inválidos");

        return ResponseEntity.badRequest().body(new ErrorResponse("VALIDATION_ERROR", error));
    }

    /**
     * Handles the {@link ProductNotFoundException} that is thrown when attempting
     * to access or manipulate a product that does not exist in the system.
     *
     * @param ex the {@link ProductNotFoundException} containing details about the product not found
     * @return a {@link ResponseEntity} containing an {@link ErrorResponse} with the error code "PRODUCT_NOT_EXISTS"
     *         and an appropriate error message, along with an HTTP status of 404 Not Found
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex) {
        log.error("Product not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("PRODUCT_NOT_EXISTS", ex.getMessage()));
    }
}
