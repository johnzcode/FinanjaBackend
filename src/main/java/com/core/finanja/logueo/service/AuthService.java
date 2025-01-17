package com.core.finanja.logueo.service;

import com.core.finanja.logueo.model.data.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface AuthService {

    Usuario register(Usuario usuario);

    Optional<String> login(String emailOrUsername, String password);

    Optional<Usuario> findById(UUID id);
}
