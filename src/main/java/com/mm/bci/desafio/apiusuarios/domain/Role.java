package com.mm.bci.desafio.apiusuarios.domain;

public enum Role {
    ROLE_ADMIN("ADMIN"), ROLE_USER("USER");
    private String value;
    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
