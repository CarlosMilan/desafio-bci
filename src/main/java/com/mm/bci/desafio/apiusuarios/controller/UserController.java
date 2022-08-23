package com.mm.bci.desafio.apiusuarios.controller;

import com.mm.bci.desafio.apiusuarios.domain.User;
import com.mm.bci.desafio.apiusuarios.dto.UserDTO;
import com.mm.bci.desafio.apiusuarios.repository.UserRepository;
import com.mm.bci.desafio.apiusuarios.security.service.UserDetailsServiceImpl;
import com.mm.bci.desafio.apiusuarios.security.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl service;

    @Autowired
    private JWTUtils jwtUtils;

    @GetMapping(value = "/sing-up")
    public ResponseEntity<String> userRegister() {
        User user = new User();
        user.setEmail("carlos@correo.com");
        user.setPassword("1234");
        user.setName("Carlos");
        user.setRole("ADMIN");
        userRepository.save(user);
        UserDetails userDetails = service.loadUserByUsername(user.getEmail());

        String token = jwtUtils.generateToken(userDetails);

        return new ResponseEntity<>(token, HttpStatus.CREATED);

    }
}
