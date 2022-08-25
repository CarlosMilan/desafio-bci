package com.mm.bci.desafio.apiusuarios.service;

import com.mm.bci.desafio.apiusuarios.Data;
import com.mm.bci.desafio.apiusuarios.domain.User;
import com.mm.bci.desafio.apiusuarios.dto.UserDTO;
import com.mm.bci.desafio.apiusuarios.dto.UserResponseDTO;
import com.mm.bci.desafio.apiusuarios.exceptions.ConstraintsException;
import com.mm.bci.desafio.apiusuarios.exceptions.UserAlreadyExistException;
import com.mm.bci.desafio.apiusuarios.repository.UserRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    @DisplayName("Find by id")
    void getUserTest() {
        UUID id = UUID.fromString("4563b561-8b7e-43a4-a918-ac8bcbab8990");
        when(userRepository.findById(id)).thenReturn(Data.createUser1());
        User user = userService.getUser(id);

        assertEquals("Charlie", user.getName());
        assertEquals("charlie_01@correo.com", user.getEmail());
        assertEquals("Jajaja57b", user.getPassword());
        assertEquals("USER", user.getRole());
        assertTrue(user.isActive());

        assertEquals(345790145L, user.getPhones().get(0).getNumber());
        assertEquals(155789456L, user.getPhones().get(1).getNumber());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Find by id Not Found")
    void getUserTest2() {
        UUID id = UUID.fromString("4563b561-8b7e-43a4-a918-ac8bcbab8990");
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        NoSuchElementException ex = assertThrows(NoSuchElementException.class, () -> userService.getUser(id));
        assertEquals("User not found", ex.getMessage());

        verify(userRepository).findById(id);
    }

    @Test
    @DisplayName("Bad password 1")
    void passwordTest1() {

        UserDTO userDTO = Data.createUserDTO1().get();
        userDTO.setPassword("nocapitallet");
        ConstraintsException ex = assertThrows(ConstraintsException.class, () -> userService.userRegister(userDTO));
        assertTrue(ex.getMessage().contains("Password must have a capital letter"));
        assertEquals(4002, ex.getCode());

        userDTO.setPassword("Nonumbers");
        ex = assertThrows(ConstraintsException.class, () -> userService.userRegister(userDTO));
        assertTrue(ex.getMessage().contains("Password must have two numbers"));
        assertEquals(4003, ex.getCode());

        userDTO.setPassword("Just0nenum");
        ex = assertThrows(ConstraintsException.class, () -> userService.userRegister(userDTO));
        assertTrue(ex.getMessage().contains("Password must have two numbers"));
        assertEquals(4003, ex.getCode());

        userDTO.setPassword("TwOcaplet");
        ex = assertThrows(ConstraintsException.class, () -> userService.userRegister(userDTO));
        assertTrue(ex.getMessage().contains("Password must have a capital letter"));
        assertEquals(4002, ex.getCode());

        userDTO.setPassword("Thr33numb3");
        ex = assertThrows(ConstraintsException.class, () -> userService.userRegister(userDTO));
        assertTrue(ex.getMessage().contains("Password must have two numbers"));
        assertEquals(4003, ex.getCode());

        verify(userRepository, never()).save(any(User.class));

    }

    @Test
    @DisplayName("Save user")
    void saveUserTest() {

        User user = Data.createUser1().get();
        when(userRepository.findByEmail(any())).thenReturn(Data.createUser1().get());
        when(userRepository.saveAndFlush(any())).thenReturn(user);

        UserDTO userDTO = Data.createUserDTO1().get();
        UserResponseDTO savedUSer = userService.userRegister(userDTO);

        assertAll(
                () -> assertNotNull(savedUSer),
                () -> assertEquals(user.getEmail(), savedUSer.getUser().getEmail()),
                () -> assertEquals(user.getName(), savedUSer.getUser().getName()),
                () -> assertEquals(user.getPassword(), savedUSer.getUser().getPassword()),
                () -> assertEquals(user.getRole(), savedUSer.getUser().getRole()),
                () -> assertEquals(user.getCreateAt(), savedUSer.getUser().getCreateAt()),
                () -> assertEquals(user.getLastLogin(), savedUSer.getUser().getLastLogin()),
                () -> assertEquals(user.isActive(), savedUSer.getUser().isActive()),
                () -> assertEquals(user.getPhones().get(0).getNumber(), savedUSer.getUser().getPhones().get(0).getNumber()),
                () -> assertEquals(user.getPhones().get(1).getNumber(), savedUSer.getUser().getPhones().get(1).getNumber())
        );

        verify(userRepository).findByEmail(any());
        verify(userRepository).saveAndFlush(any(User.class));

    }

    @Test
    @DisplayName("Existing User")
    void existingUserTest() {

        User user = Data.createUser1().get();
        when(userRepository.findByEmail(any())).thenReturn(Data.createUser1().get());
        when(userRepository.saveAndFlush(any())).thenReturn(user);
        when(userRepository.existsByEmail(any())).thenReturn(true);

        UserDTO userDTO = Data.createUserDTO1().get();
        UserAlreadyExistException ex = assertThrows(UserAlreadyExistException.class, () -> userService.userRegister(userDTO));
        assertEquals("There is already a user with the email " + userDTO.getEmail(), ex.getMessage());

        verify(userRepository).existsByEmail(any(String.class));
        verify(userRepository, never()).findByEmail(any(String.class));
        verify(userRepository, never()).saveAndFlush(any(User.class));

    }
}
