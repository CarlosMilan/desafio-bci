package com.mm.bci.desafio.apiusuarios.exceptions;


public class ConstraintsException extends RuntimeException{
    private int code;
    public ConstraintsException(String message) {
        super(message);
    }

    public ConstraintsException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
