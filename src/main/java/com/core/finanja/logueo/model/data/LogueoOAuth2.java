package com.core.finanja.logueo.model.data;

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

    private String proveedor;

    @Column(name = "external_id")
    private String externalId;

    private String token;
}
