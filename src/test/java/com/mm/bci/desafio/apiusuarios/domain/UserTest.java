package com.mm.bci.desafio.apiusuarios.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @Test
    void userCreationTest() {
        User user = new User();
        user.setName("Charlie");
        user.setEmail("asd");

        System.out.println("user = " + user);
    }
}
