package com.jamipariseva.exception;

import com.jamipariseva.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final java.util.Map<String, String> HTTP_CODES_MAP = java.util.Map.ofEntries(
        java.util.Map.entry("200 OK", "Request succeeded"),
        java.util.Map.entry("201 Created", "Resource created successfully (most common for POST)"),
        java.util.Map.entry("202 Accepted", "Request accepted, processing later"),
        java.util.Map.entry("204 No Content", "Success but no response body"),
        java.util.Map.entry("400 Bad Request", "Invalid request/body format"),
        java.util.Map.entry("401 Unauthorized", "Authentication required"),
        java.util.Map.entry("403 Forbidden", "Authenticated but no permission"),
        java.util.Map.entry("404 Not Found", "Endpoint/resource doesn't exist"),
        java.util.Map.entry("405 Method Not Allowed", "POST used where it's not allowed"),
        java.util.Map.entry("409 Conflict", "Resource already exists"),
        java.util.Map.entry("415 Unsupported Media Type", "Wrong Content-Type"),
        java.util.Map.entry("422 Unprocessable Entity", "Validation failed"),
        java.util.Map.entry("500 Internal Server Error", "Generic server error"),
        java.util.Map.entry("501 Not Implemented", "Feature not implemented"),
        java.util.Map.entry("502 Bad Gateway", "Upstream server error"),
        java.util.Map.entry("503 Service Unavailable", "Server down/overloaded"),
        java.util.Map.entry("504 Gateway Timeout", "Gateway Timeout")
    );

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(BadRequestException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(ex.getMessage(), HTTP_CODES_MAP));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .orElse("Validation failed");
        return ResponseEntity.badRequest().body(ApiResponse.fail(message, HTTP_CODES_MAP));
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleMessageNotReadable(org.springframework.http.converter.HttpMessageNotReadableException ex) {
        String causeMessage = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
        return ResponseEntity.badRequest().body(ApiResponse.fail("Malformed JSON or invalid request format: " + causeMessage, HTTP_CODES_MAP));
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(jakarta.validation.ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .findFirst()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .orElse("Validation failed");
        return ResponseEntity.badRequest().body(ApiResponse.fail(message, HTTP_CODES_MAP));
    }

    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingParams(org.springframework.web.bind.MissingServletRequestParameterException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(ex.getMessage(), HTTP_CODES_MAP));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex) {
        log.error("Unexpected error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail("Internal server error. Please try again later."));
    }
}
