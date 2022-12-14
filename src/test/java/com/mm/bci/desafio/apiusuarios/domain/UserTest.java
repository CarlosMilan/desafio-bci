package com.mm.bci.desafio.apiusuarios.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("User Tests")
class UserTest {

    @Test
    void userCreationTest() {

        LocalDateTime now = LocalDateTime.now();

        User user = new User();
        user.setName("Charlie");
        user.setEmail("asd");
        user.setLastLogin(now);
        user.setCreateAt(now);
        user.setEmail("charlie_01@correo.com");
        user.setPassword("Jajaja57b");
        user.setRole("USER");
        user.setActive(true);

        Phone phone1 = new Phone();
        phone1.setId(UUID.randomUUID());
        phone1.setUser(user);
        phone1.setNumber(345790145L);
        phone1.setCityCode(261);
        phone1.setCountryCode("+54");

        Phone phone2 = new Phone();
        phone2.setId(UUID.randomUUID());
        phone2.setUser(user);
        phone2.setNumber(155789456L);
        phone2.setCityCode(262);
        phone2.setCountryCode("+54");

        user.setPhones(Arrays.asList(phone1, phone2));

        user.init();

        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals("charlie_01@correo.com", user.getEmail()),
                () -> assertEquals("Charlie", user.getName()),
                () -> assertEquals("Jajaja57b", user.getPassword()),
                () -> assertEquals("USER", user.getRole()),
                () -> assertNotNull(user.getCreateAt()),
                () -> assertNotNull(user.getLastLogin()),
                () -> assertTrue(user.isActive()),
                () -> assertEquals(345790145L, user.getPhones().get(0).getNumber()),
                () -> assertEquals(261, user.getPhones().get(0).getCityCode()),
                () -> assertEquals("+54", user.getPhones().get(0).getCountryCode()),
                () -> assertEquals(155789456L, user.getPhones().get(1).getNumber()),
                () -> assertEquals(262, user.getPhones().get(1).getCityCode()),
                () -> assertEquals("+54", user.getPhones().get(1).getCountryCode()),
                () -> assertNotNull(user.getPhones().get(0).getId()),
                () -> assertEquals(user, user.getPhones().get(1).getUser()),
                () -> assertTrue(user.isActive()),
                () -> assertNotNull(user.getCreateAt())
        );

    }
}
