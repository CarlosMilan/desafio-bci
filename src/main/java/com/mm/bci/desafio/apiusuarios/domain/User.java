package com.mm.bci.desafio.apiusuarios.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotNull(message = "The name cannot be empty")
    private String name;

    @NotNull(message = "The email cannot be empty")
    @Pattern(regexp = "^[0-9a-zA-Z.\\-_]+@[0-9a-zA-Z]+(\\.[a-zA-Z]+)+$", message = "The email has an incorrect format")
    private String email;

    @NotNull(message = "The password cannot be empty")
    private String password;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Phone> phones;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "is_active")
    private boolean isActive;
}
