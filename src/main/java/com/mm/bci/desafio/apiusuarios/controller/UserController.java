package com.mm.bci.desafio.apiusuarios.controller;

import com.mm.bci.desafio.apiusuarios.domain.User;
import com.mm.bci.desafio.apiusuarios.dto.LoginResponseDTO;
import com.mm.bci.desafio.apiusuarios.dto.UserDTO;
import com.mm.bci.desafio.apiusuarios.dto.UserResponseDTO;
import com.mm.bci.desafio.apiusuarios.repository.UserRepository;
import com.mm.bci.desafio.apiusuarios.security.service.UserDetailsServiceImpl;
import com.mm.bci.desafio.apiusuarios.security.utils.JWTUtils;
import com.mm.bci.desafio.apiusuarios.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping(value = "/sing-up")
    public ResponseEntity<UserResponseDTO> userRegister(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(service.userRegister(userDTO), HttpStatus.CREATED);
    }

    @GetMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> login(HttpServletRequest request) {
        return new ResponseEntity<>(service.userLogged(request), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        return new ResponseEntity<>(service.getUser(id), HttpStatus.OK);
    }

    @GetMapping(value = "/message")
    public ResponseEntity<String> message() {

        String message = "Mensaje privado";

        return new ResponseEntity<>(message, HttpStatus.CREATED);

    }


}
