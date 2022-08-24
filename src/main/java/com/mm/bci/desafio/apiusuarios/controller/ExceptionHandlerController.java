package com.mm.bci.desafio.apiusuarios.controller;

import com.mm.bci.desafio.apiusuarios.dto.Error;
import com.mm.bci.desafio.apiusuarios.exceptions.ConstraintsException;
import com.mm.bci.desafio.apiusuarios.exceptions.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception ex) {
        Error error = new Error(new Timestamp(new Date().getTime()),HttpStatus.INTERNAL_SERVER_ERROR.value() , ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(ConstraintsException.class)
    public ResponseEntity<Error> handleConstraintsException(ConstraintsException ex) {
        Error error = new Error();
        error.setCode(ex.getCode());
        error.setDetail(ex.getMessage());
        error.setTimestamp(new Timestamp(new Date().getTime()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<Error>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<Error> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(e -> {
            Error error = new Error();
            error.setTimestamp(new Timestamp(new Date().getTime()));
            error.setDetail(e.getMessage());
            error.setCode(HttpStatus.BAD_REQUEST.value());
            errors.add(error);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Error> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        Error error = new Error(new Timestamp(new Date().getTime()),HttpStatus.BAD_REQUEST.value() , ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Error> handleNoSuchElementException(NoSuchElementException ex) {
        Error error = new Error(new Timestamp(new Date().getTime()),HttpStatus.BAD_REQUEST.value() , ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
