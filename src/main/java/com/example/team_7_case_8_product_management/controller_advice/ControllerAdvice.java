package com.example.team_7_case_8_product_management.controller_advice;

import com.example.team_7_case_8_product_management.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

}
