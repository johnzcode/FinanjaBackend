package com.core.finanja.logueo.service;

import com.core.finanja.logueo.model.AuthResponse;
import com.core.finanja.logueo.model.data.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface AuthService {

    AuthResponse register(Usuario usuario) throws Exception;

    Optional<String> login(String emailOrUsername, String password);

    Optional<Usuario> findById(UUID id);
}
