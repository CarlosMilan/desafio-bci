package com.mm.bci.desafio.apiusuarios.service;

import com.mm.bci.desafio.apiusuarios.domain.User;
import com.mm.bci.desafio.apiusuarios.dto.LoginResponseDTO;
import com.mm.bci.desafio.apiusuarios.dto.UserDTO;
import com.mm.bci.desafio.apiusuarios.dto.UserResponseDTO;

import java.util.UUID;

public interface UserService {
    User getUser(UUID id);
    UserResponseDTO userRegister(UserDTO userDTO);
    LoginResponseDTO login(String token);
}
