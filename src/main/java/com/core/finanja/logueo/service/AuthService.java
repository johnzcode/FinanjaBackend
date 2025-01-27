package com.core.finanja.logueo.service;

import com.core.finanja.logueo.model.AuthResponse;
import com.core.finanja.logueo.model.DTO.RecoverPwdRequest;
import com.core.finanja.logueo.model.data.Usuario;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

import java.util.Optional;
import java.util.UUID;

public interface AuthService {

    Optional<OAuth2AccessTokenResponse> login(String emailOrUsername, String password);

    AuthResponse register(Usuario usuario) throws Exception;

    AuthResponse sendReciverEmail(String user, String email) throws Exception;

    Optional<Usuario> findById(UUID id);
}
