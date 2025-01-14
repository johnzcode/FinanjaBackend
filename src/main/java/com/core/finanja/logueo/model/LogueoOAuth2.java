package com.core.finanja.logueo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "logueo_oauth2")
@Data
public class LogueoOAuth2 {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 50)
    private String proveedor;

    @Column(name = "external_id", nullable = false, length = 255)
    private String externalId;

    private String token;
}
