package pl.training.cloud.shop.commons;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.training.cloud.shop.ExceptionTransferObject;
import pl.training.cloud.shop.orders.OrderNotFoundException;
import pl.training.cloud.shop.products.ProductNotFoundException;

import java.time.LocalDateTime;
import java.util.Locale;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionTransferObject> onException(Exception exception, Locale locale) {
        exception.printStackTrace();
        return createResponse(exception, INTERNAL_SERVER_ERROR, locale);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionTransferObject> onProductNotFoundException(ProductNotFoundException exception, Locale locale) {
        return createResponse(exception, NOT_FOUND, locale);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ExceptionTransferObject> onOrderNotFoundException(OrderNotFoundException exception, Locale locale) {
        return createResponse(exception, NOT_FOUND, locale);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionTransferObject> onMethodArgumentNotValidException(MethodArgumentNotValidException exception, Locale locale) {
        return createResponse(exception, BAD_REQUEST, locale);
    }

    private ResponseEntity<ExceptionTransferObject> createResponse(Exception exception, HttpStatus status, Locale locale) {
        var exceptionName = exception.getClass().getSimpleName();
        String description;
        try {
            description = messageSource.getMessage(exception.getClass().getSimpleName(), null, locale);
        } catch (NoSuchMessageException otherException) {
            description = exceptionName;

        }
        exception.printStackTrace();
        return ResponseEntity.status(status).body(new ExceptionTransferObject(description, LocalDateTime.now()));
    }

}