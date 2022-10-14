package com.mm.bci.desafio.apiusuarios;

import com.mm.bci.desafio.apiusuarios.domain.Phone;
import com.mm.bci.desafio.apiusuarios.domain.User;
import com.mm.bci.desafio.apiusuarios.dto.PhoneDTO;
import com.mm.bci.desafio.apiusuarios.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

public class Data {

    public static Optional<User> createUser1() {
        User user = new User();
        user.setName("Charlie");
        user.setLastLogin(LocalDateTime.now());
        user.setCreateAt(LocalDateTime.now());
        user.setEmail("charlie_01@correo.com");
        user.setPassword("Jajaja57b");
        user.setRole("USER");
        user.setActive(true);

        Phone phone1 = new Phone();
        phone1.setUser(user);
        phone1.setNumber(345790145L);
        phone1.setCityCode(261);
        phone1.setCountryCode("+54");

        Phone phone2 = new Phone();
        phone2.setUser(user);
        phone2.setNumber(155789456L);
        phone2.setCityCode(262);
        phone2.setCountryCode("+54");

        user.setPhones(Arrays.asList(phone1, phone2));
        return Optional.of(user);
    }

    public static Optional<UserDTO> createUserDTO1() {
        UserDTO user = new UserDTO();
        user.setName("Charlie");

        user.setEmail("charlie_01@correo.com");
        user.setPassword("Jajaja57b");

        PhoneDTO phone1 = new PhoneDTO();

        phone1.setNumber(345790145L);
        phone1.setCitycode(261);
        phone1.setCountryCode("+54");

        PhoneDTO phone2 = new PhoneDTO();
        phone2.setNumber(155789456L);
        phone2.setCitycode(262);
        phone2.setCountryCode("+54");

        user.setPhones(Arrays.asList(phone1, phone2));
        return Optional.of(user);
    }
}
