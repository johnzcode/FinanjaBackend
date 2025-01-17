package com.core.finanja.logueo.model.data;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "nombres", nullable = false, length = 50)
    private String names;

    @Column(name = "apellidos", nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "usuario", unique = true, length = 50)
    private String user;

    @Column(nullable = false)
    private String password;

    private String sexo;

    @Column(name = "fecha_Registro", updatable = false)
    private LocalDate fechaRegistro = LocalDate.now();

    private String moneda;
}
