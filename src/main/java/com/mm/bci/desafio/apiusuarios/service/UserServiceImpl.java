package com.mm.bci.desafio.apiusuarios.service;

import com.mm.bci.desafio.apiusuarios.domain.User;
import com.mm.bci.desafio.apiusuarios.dto.*;
import com.mm.bci.desafio.apiusuarios.exceptions.ConstraintsException;
import com.mm.bci.desafio.apiusuarios.exceptions.UserAlreadyExistException;
import com.mm.bci.desafio.apiusuarios.mapper.UserMapper;
import com.mm.bci.desafio.apiusuarios.repository.UserRepository;
import com.mm.bci.desafio.apiusuarios.security.service.UserDetailsServiceImpl;
import com.mm.bci.desafio.apiusuarios.security.utils.JWTUtils;
import com.mm.bci.desafio.apiusuarios.utils.Constants;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final JWTUtils jwtUtils;
    private final UserMapper mapper;

    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public UserResponseDTO userRegister(UserDTO userDTO) {
        UserResponseDTO response = new UserResponseDTO();
        checkPassword(userDTO.getPassword());
        User user = saveUser(userDTO);

        response.setUser(user);
        response.setToken(createToken(user));
        return response;
    }

    private User saveUser(UserDTO userDTO) {
        User user = mapper.toUser(userDTO);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("There is already a user with the email " + user.getEmail());
        }
        return userRepository.saveAndFlush(user);
    }

    private void checkPassword(String password) {
        if (!checkCapitalsAmount(password)) {
            throw new ConstraintsException(4002,"Password must have a capital letter");
        }
        if (!checkNumbersAmount(password)) {
            throw new ConstraintsException(4003,"Password must have two numbers");
        }
    }

    private String createToken(User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        return jwtUtils.generateToken(userDetails);
    }

    private boolean checkCapitalsAmount(String password) {
        Matcher matcher = evaluateRegex(password, Constants.CAPITAL_REGEX);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count == 1;
    }

    private boolean checkNumbersAmount(String password) {
        Matcher matcher = evaluateRegex(password, Constants.NUMBER_REGEX);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count == 2;
    }

    private Matcher evaluateRegex(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(content);
    }

    public LoginResponseDTO login(String token) {

        String username = jwtUtils.getUsername(token);
        User user = userRepository.findByEmail(username);
        String newToken = createToken(user);
        user.setLastLogin(LocalDateTime.now());
        return mapper.createLoginResponse(userRepository.saveAndFlush(user), newToken);
    }
}
