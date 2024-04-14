package com.example.demo.base;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public final class RestExceptionHandler {

    @ExceptionHandler(value = {BackendException.class})
    private ResponseEntity<Object> handleBackendException(Exception ex) {
        final BackendException backendEx = (BackendException) (ex);
            log.warn("Backend Exception: {}", ex.getMessage());
            ex.printStackTrace();
        return new ResponseEntity<>(HttpResponse.errorWithMessage(backendEx.getCode(), backendEx.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    private ResponseEntity<Object> handleNullPointerException(Exception ex) {
        final NullPointerException NPE = (NullPointerException) (ex);

        // We cannot let client know how we work internally, so we just return a generic error
        // todo: add logging
        log.error("Null Pointer Exception: {}", ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(HttpResponse.errorWithMessage(-1, "Internal server error"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpResponse handleJsonMappingException(Exception rex) {
        MethodArgumentNotValidException ex = (MethodArgumentNotValidException) rex;
        BindingResult result = ex.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            if (errors != null) {
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    log.warn("Bad Request Parameters: dto entity [{}],field [{}],message [{}]", fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
                    stringBuilder.append(fieldError.getDefaultMessage());
                    ex.printStackTrace();
                });
            }
        }
        return HttpResponse.errorWithMessage(400, stringBuilder.toString());
    }

    @ExceptionHandler(Exception.class)
    public HttpResponse handleRowException(Exception ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        return HttpResponse.errorWithMessage(500, "系统错误");
    }

}