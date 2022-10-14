package com.mm.bci.desafio.apiusuarios.mapper;

import com.mm.bci.desafio.apiusuarios.domain.Phone;
import com.mm.bci.desafio.apiusuarios.domain.Role;
import com.mm.bci.desafio.apiusuarios.domain.User;
import com.mm.bci.desafio.apiusuarios.dto.LoginResponseDTO;
import com.mm.bci.desafio.apiusuarios.dto.PhoneDTO;
import com.mm.bci.desafio.apiusuarios.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public User toUser(UserDTO userDTO) {
        User user = new User();
        user.setRole(Role.ROLE_USER.getValue());
        user.setName(userDTO.getName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        List<Phone> phones = userDTO.getPhones().stream()
                .map(phoneDto -> toPhone(phoneDto, user))
                .collect(Collectors.toList());
        user.setPhones(phones);

        return user;
    }

    private Phone toPhone(PhoneDTO phoneDTO, User user) {
        Phone phone = new Phone();
        phone.setCityCode(phoneDTO.getCitycode());
        phone.setCountryCode(phoneDTO.getCountryCode());
        phone.setNumber(phoneDTO.getNumber());
        phone.setUser(user);
        return phone;
    }

    public LoginResponseDTO createLoginResponse(User user, String token) {

        LoginResponseDTO response = new LoginResponseDTO();
        response.setId(user.getId());
        response.setCreated(user.getCreateAt());
        response.setLastLogin(user.getLastLogin());
        response.setToken(token);
        response.setActive(user.isActive());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhones(user.getPhones());
        response.setPassword(user.getPassword());
        return response;
    }
}
