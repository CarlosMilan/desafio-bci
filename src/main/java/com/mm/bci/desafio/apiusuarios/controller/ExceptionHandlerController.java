package com.mm.bci.desafio.apiusuarios.controller;

import com.mm.bci.desafio.apiusuarios.dto.Error;
import com.mm.bci.desafio.apiusuarios.exceptions.ConstraintsException;
import com.mm.bci.desafio.apiusuarios.exceptions.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ConstraintsException.class)
    public ResponseEntity<Error> handleConstraintsException(ConstraintsException ex) {
        Error error = new Error();
        error.setCode(ex.getCode());
        error.setDetail(ex.getMessage());
        error.setTimestamp(new Timestamp(new Date().getTime()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Error>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Error> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> {
            Error error = new Error();
            error.setTimestamp(new Timestamp(new Date().getTime()));
            error.setDetail(e.getField() + " : " + e.getDefaultMessage());
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

}
