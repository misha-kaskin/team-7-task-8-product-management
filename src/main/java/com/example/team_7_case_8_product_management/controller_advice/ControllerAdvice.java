package com.example.team_7_case_8_product_management.controller_advice;

import com.example.team_7_case_8_product_management.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

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

}
