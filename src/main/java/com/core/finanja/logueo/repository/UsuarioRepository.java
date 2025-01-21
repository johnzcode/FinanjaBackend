package com.core.finanja.logueo.repository;

import com.core.finanja.logueo.model.data.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByUser(String usuario);

    boolean existsByEmail(String email);

    boolean existsByUser(String usuario);
}
