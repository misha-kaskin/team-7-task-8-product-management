package com.example.team_7_case_8_product_management.controller_advice;

import com.example.team_7_case_8_product_management.exception.*;
import com.example.team_7_case_8_product_management.model.cart.ExtendCartDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.springframework.http.HttpStatus.*;

@CrossOrigin
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(UserNotAuthException.class)
    public ResponseEntity<ErrorMessage> notAuthException() {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(new ErrorMessage("Токен не действителен"));
    }

    @ExceptionHandler(InvalidUserRoleException.class)
    public ResponseEntity<ErrorMessage> invalidUserRoleException() {
        return ResponseEntity
                .status(CONFLICT)
                .body(new ErrorMessage("Устаревшая роль пользователя"));
    }

    @ExceptionHandler(TokenNotValidException.class)
    public ResponseEntity<ErrorMessage> invalidTokenException() {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(new ErrorMessage("Недопустимый токен"));
    }

    @ExceptionHandler(MismatchRoleException.class)
    public ResponseEntity<ErrorMessage> mismatchRoleException() {
        return ResponseEntity
                .status(NOT_ACCEPTABLE)
                .body(new ErrorMessage("Неподходящая роль"));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> alreadyExistsException() {
        return ResponseEntity
                .status(CONFLICT)
                .body(new ErrorMessage("Пользователь с таким логином уже существует"));
    }

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<ErrorMessage> userNotExistsException() {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(new ErrorMessage("Пользователя с таким логином не существует"));
    }

    @ExceptionHandler(IncorrectLoginException.class)
    public ResponseEntity<ErrorMessage> incorrectLoginException() {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(new ErrorMessage("Неправильный пароль"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorMessage> handleError405() {
        return ResponseEntity
                .status(NOT_ACCEPTABLE)
                .body(new ErrorMessage("Неправильный URL"));
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorMessage> itemNotFoundException() {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(new ErrorMessage("Не найден товар"));
    }

    @ExceptionHandler(CantRemoveFileException.class)
    public ResponseEntity<ErrorMessage> cantRemoveFileException() {
        return ResponseEntity
                .status(UNPROCESSABLE_ENTITY)
                .body(new ErrorMessage("Не удается удалить файл, пожалуйста, попробуйте еще раз"));
    }

    @ExceptionHandler(CantCreateFileException.class)
    public ResponseEntity<ErrorMessage> cantCreateFileException() {
        return ResponseEntity
                .status(UNPROCESSABLE_ENTITY)
                .body(new ErrorMessage("Не удается создать файл, пожалуйста, попробуйте еще раз"));
    }

    @ExceptionHandler(CantFindFileException.class)
    public ResponseEntity<ErrorMessage> cantFindFileException() {
        return ResponseEntity
                .status(UNPROCESSABLE_ENTITY)
                .body(new ErrorMessage("Не удается найти файл, пожалуйста, попробуйте еще раз"));
    }

    @ExceptionHandler(CartUserIdMismatchException.class)
    public ResponseEntity<ErrorMessage> cartUserIdMismatchException() {
        return ResponseEntity
                .status(UNPROCESSABLE_ENTITY)
                .body(new ErrorMessage("Не соотвествия идентификаторов"));
    }

    @ExceptionHandler(TooManyItemsException.class)
    public ResponseEntity<ExtendCartDto> tooManyItemsException(TooManyItemsException e) {
        return ResponseEntity
                .status(UNPROCESSABLE_ENTITY)
                .body(e.getExtendCartDto());
    }

    @ExceptionHandler(UserNotFoundByIdException.class)
    public ResponseEntity<ErrorMessage> userNotFoundByIdException() {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(new ErrorMessage("Пользователя с таким id не существует"));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorMessage> orderNotFoundException() {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(new ErrorMessage("Заказа с таким id не существует"));
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<ErrorMessage> notEnoughMoneyException() {
        return ResponseEntity
                .status(UNPROCESSABLE_ENTITY)
                .body(new ErrorMessage("У пользователя на балансе недостаточно средств для покупки"));
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<ErrorMessage> emptyCartException() {
        return ResponseEntity
                .status(UNPROCESSABLE_ENTITY)
                .body(new ErrorMessage("Пустая корзина"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> onMethodArgumentNotValidException(
            MethodArgumentNotValidException  e
    ) {
        List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ValidationErrorResponse(violations));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> onConstraintValidationException(
            ConstraintViolationException e
    ) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ValidationErrorResponse(violations));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> dataIntegrityViolationException() {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ErrorMessage("Несуществующий объект"));
    }
}
