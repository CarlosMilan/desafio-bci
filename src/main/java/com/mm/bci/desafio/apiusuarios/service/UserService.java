package com.mm.bci.desafio.apiusuarios.service;

import com.mm.bci.desafio.apiusuarios.domain.Phone;
import com.mm.bci.desafio.apiusuarios.domain.Role;
import com.mm.bci.desafio.apiusuarios.domain.User;
import com.mm.bci.desafio.apiusuarios.dto.Error;
import com.mm.bci.desafio.apiusuarios.dto.PhoneDTO;
import com.mm.bci.desafio.apiusuarios.dto.UserDTO;
import com.mm.bci.desafio.apiusuarios.dto.UserResponseDTO;
import com.mm.bci.desafio.apiusuarios.exceptions.ConstraintsException;
import com.mm.bci.desafio.apiusuarios.exceptions.UserAlreadyExistException;
import com.mm.bci.desafio.apiusuarios.repository.UserRepository;
import com.mm.bci.desafio.apiusuarios.security.service.UserDetailsServiceImpl;
import com.mm.bci.desafio.apiusuarios.security.utils.JWTUtils;
import com.mm.bci.desafio.apiusuarios.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JWTUtils jwtUtils;

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
        Matcher matcher = pattern.matcher(content);
        return matcher;
    }

    public User saveUser(UserDTO userDTO) {
        User user = toUser(userDTO);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("There is already a user with the email " + user.getEmail());
        }
        User savedUser = userRepository.saveAndFlush(user);

        return savedUser;
    }

    private User toUser(UserDTO userDTO) {
        User user = new User();
        List<Phone> phones = new ArrayList<>();
        for (PhoneDTO phoneDTO: userDTO.getPhones()) {
            Phone phone = new Phone();
            phone.setCitycode(phoneDTO.getCitycode());
            phone.setCountryCode(phoneDTO.getCountryCode());
            phone.setNumber(phoneDTO.getNumber());
            phone.setUser(user);
            phones.add(phone);
        }
        user.setPhones(phones);
        user.setRole(Role.ROLE_USER.getValue());
        user.setName(userDTO.getName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());

        return user;
    }
}
