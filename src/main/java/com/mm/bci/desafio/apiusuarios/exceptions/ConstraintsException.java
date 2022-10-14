package com.mm.bci.desafio.apiusuarios.exceptions;


public class ConstraintsException extends RuntimeException{
    private final int code;
    public ConstraintsException(int code, String message) {
        super(message);
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
