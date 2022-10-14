package com.mm.bci.desafio.apiusuarios.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.mm.bci.desafio.apiusuarios.Data;
import com.mm.bci.desafio.apiusuarios.domain.User;
import com.mm.bci.desafio.apiusuarios.dto.UserDTO;
import com.mm.bci.desafio.apiusuarios.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Controller Tests")
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.mm.bci.desafio.apiusuarios"})
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

//    @MockBean
//    private UserService service;

    @MockBean
    private UserRepository repository;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Sing Up")
    void singU1pTest() throws Exception {
        UUID id = UUID.fromString("4563b561-8b7e-43a4-a918-ac8bcbab8990");

        when(repository.findByEmail(any())).thenReturn(Data.createUser1().get());

        User user = Data.createUser1().get();
        user.setId(id);
        when(repository.saveAndFlush(any())).thenReturn(user);


        UserDTO userDTO = Data.createUserDTO1().get();
        mvc.perform(post("/users/sing-up").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user.name").value("Charlie"))
                .andExpect(jsonPath("$.user.id").value(id.toString()))
                .andExpect(jsonPath("$.user.email").value("charlie_01@correo.com"))
                .andExpect(jsonPath("$.user.phones[0].number").value(345790145))
                .andExpect(jsonPath("$.user.phones[0].cityCode").value(261))
                .andExpect(jsonPath("$.user.phones[1].number").value(155789456))
                .andExpect(jsonPath("$.user.phones[1].cityCode").value(262));

    }

    @Test
    @DisplayName("Sing Up - User Already exists")
    void singUp2Test() throws Exception {
        UUID id = UUID.fromString("4563b561-8b7e-43a4-a918-ac8bcbab8990");

        when(repository.findByEmail(any())).thenReturn(Data.createUser1().get());
        when(repository.existsByEmail(any())).thenReturn(true);
        User user = Data.createUser1().get();
        user.setId(id);
        when(repository.saveAndFlush(any())).thenReturn(user);


        UserDTO userDTO = Data.createUserDTO1().get();

        mvc.perform(post("/users/sing-up").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.detail").value("There is already a user with the email " + userDTO.getEmail()));

    }

    @Test
    @DisplayName("Sing Up - ConstraintException 1")
    void singUpException1Test() throws Exception {

        UserDTO userDTO = Data.createUserDTO1().get();
        userDTO.setPassword("nocapsgg12");
        mvc.perform(post("/users/sing-up").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(4002))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.detail").value("Password must have a capital letter"));

    }

    @Test
    @DisplayName("Sing Up - ConstraintException 2")
    void singUpException2Test() throws Exception {

        UserDTO userDTO = Data.createUserDTO1().get();
        userDTO.setPassword("Nonumbers");
        mvc.perform(post("/users/sing-up").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(4003))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.detail").value("Password must have two numbers"));

    }

    @Test
    @DisplayName("Sing Up - MethodArgumentNotValidException 1")
    void singUpException3Test() throws Exception {

        UserDTO userDTO = Data.createUserDTO1().get();
        userDTO.setPassword("Numb12");
        mvc.perform(post("/users/sing-up").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$[0].timestamp").exists())
                .andExpect(jsonPath("$[0].detail").value("password : The password must be between 8 and 12 characters"));

    }

    @Test
    @DisplayName("Sing Up - MethodArgumentNotValidException 2")
    void singUpException4Test() throws Exception {

        UserDTO userDTO = Data.createUserDTO1().get();
        userDTO.setPassword("Numb12");
        userDTO.setEmail("test_correo.com");
        userDTO.getPhones().get(0).setCountryCode(null);
        userDTO.getPhones().get(0).setNumber(null);
        userDTO.getPhones().get(1).setNumber(null);
        userDTO.getPhones().get(1).setCitycode(null);
        mvc.perform(post("/users/sing-up").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$[0].timestamp").exists())
                .andExpect(jsonPath("$[0].detail").exists())
                .andExpect(jsonPath("$[1].code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$[1].timestamp").exists())
                .andExpect(jsonPath("$[1].detail").exists())
                .andExpect(jsonPath("$", hasSize(6)));
    }


    @Test
    @DisplayName("Login")
    void loginTest() throws Exception {
        UUID id = UUID.fromString("4563b561-8b7e-43a4-a918-ac8bcbab8990");

        when(repository.findByEmail(any())).thenReturn(Data.createUser1().get());

        User user = Data.createUser1().get();
        user.setId(id);
        when(repository.saveAndFlush(any())).thenReturn(user);


        UserDTO userDTO = Data.createUserDTO1().get();
        MvcResult response = mvc.perform(post("/users/sing-up").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDTO))).andReturn();

        String token = JsonPath.read(response.getResponse().getContentAsString(), "$.token");

        mvc.perform(get("/users/login").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));

    }
}
