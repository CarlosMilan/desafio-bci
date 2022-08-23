package com.mm.bci.desafio.apiusuarios.service;

import com.mm.bci.desafio.apiusuarios.domain.Phone;
import com.mm.bci.desafio.apiusuarios.domain.User;
import com.mm.bci.desafio.apiusuarios.dto.PhoneDTO;
import com.mm.bci.desafio.apiusuarios.dto.UserDTO;
import com.mm.bci.desafio.apiusuarios.dto.UserResponseDTO;
import com.mm.bci.desafio.apiusuarios.repository.UserRepository;
import com.mm.bci.desafio.apiusuarios.security.service.UserDetailsServiceImpl;
import com.mm.bci.desafio.apiusuarios.security.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public UserResponseDTO userRegister(UserDTO userDTO) {
        UserResponseDTO response = new UserResponseDTO();
        User user = saveUser(userDTO);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtils.generateToken(userDetails);
        response.setUser(user);
        response.setToken(token);
        return response;
    }

    public User saveUser(UserDTO userDTO) {
        User user = toUser(userDTO);
        User savedUser = userRepository.save(user);

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
            phones.add(phone);
        }
        user.setPhones(phones);
        user.setRole("USER");
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());

        return user;
    }
}
