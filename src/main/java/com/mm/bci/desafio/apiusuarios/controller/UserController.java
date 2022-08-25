package com.mm.bci.desafio.apiusuarios.controller;

import com.mm.bci.desafio.apiusuarios.domain.User;
import com.mm.bci.desafio.apiusuarios.dto.LoginResponseDTO;
import com.mm.bci.desafio.apiusuarios.dto.UserDTO;
import com.mm.bci.desafio.apiusuarios.dto.UserResponseDTO;
import com.mm.bci.desafio.apiusuarios.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping(value = "/sing-up", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDTO> userRegister(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(service.userRegister(userDTO), HttpStatus.CREATED);
    }

    @GetMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> login(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        LoginResponseDTO responseDTO = service.login(token);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
