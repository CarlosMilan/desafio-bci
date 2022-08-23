package com.mm.bci.desafio.apiusuarios.dto;

import com.mm.bci.desafio.apiusuarios.domain.User;

public class UserResponseDTO {
    private User user;
    private String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
