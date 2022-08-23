package com.mm.bci.desafio.apiusuarios.dto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UserDTO {

    private String name;

    @NotNull(message = "The email cannot be empty")
    @Pattern(regexp = "^[0-9a-zA-Z.\\-_]+@[0-9a-zA-Z]+(\\.[a-zA-Z]+)+$", message = "The email has an incorrect format")
    private String email;

    @NotNull(message = "The password cannot be empty")
    @Size(min = 8, max = 12, message = "The password must be between 8 and 12 characters")
    private String password;
    private List<PhoneDTO> phones;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }
}
