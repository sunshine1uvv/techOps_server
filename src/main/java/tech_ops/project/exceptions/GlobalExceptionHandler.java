package tech_ops.project.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ApiError("Ошибка заполнения полей", errors);
    }

    @ExceptionHandler(LockedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleLockedException(LockedException e) {
        return new ApiError("Учётная запись заблокирована. Обратитесь к главному администратору.", null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictExceptions(DataIntegrityViolationException ex) {
        return new ApiError("Ошибка сохранения: Нарушение уникальности данных (такой номер или код уже существует)", null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleGeneralExceptions(Exception ex) {
        return new ApiError("Внутренняя ошибка сервера: " + ex.getMessage(), null);
    }

    @ExceptionHandler(InventoryConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleInventoryConflict(InventoryConflictException ex) {
        return new ApiError(ex.getMessage(), null);
    }
}
