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

    @Column(name = "nombres")
    private String names;

    @Column(name = "apellidos")
    private String lastName;

    private String email;

    @Column(name = "usuario")
    private String user;

    private String password;


    private String sexo;

    @Column(name = "fecha_Registro")
    private LocalDate fechaRegistro = LocalDate.now();

    private String moneda;
}
