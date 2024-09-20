package org.mitenkov.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Table(name = "users")
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    @Email
    private String email;

    private String password;
}

