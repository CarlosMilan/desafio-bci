package com.mm.bci.desafio.apiusuarios.dto;

import com.mm.bci.desafio.apiusuarios.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private User user;
    private String token;
}
