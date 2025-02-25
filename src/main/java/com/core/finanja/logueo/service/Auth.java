package com.core.finanja.logueo.service;

import com.core.finanja.config.security.JwtUtils;
import com.core.finanja.logueo.model.AuthResponse;
import com.core.finanja.logueo.model.data.Usuario;
import com.core.finanja.logueo.repository.UsuarioRepository;
import com.core.finanja.response.DetailBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class Auth implements AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Optional<OAuth2AccessTokenResponse> login(String emailOrUsername, String password) {
        Usuario user;

        try{
            Optional<Usuario> userOptional = usuarioRepository.findByEmail(emailOrUsername)
                    .or(() -> usuarioRepository.findByUser(emailOrUsername));

            if (userOptional.isPresent()) {
                user = userOptional.get();
                if (passwordEncoder.matches(password, user.getPassword())) {
                    return Optional.of(jwtUtils.generateTokens(user.getUser()));
                }else {
                    System.out.println("Password invalida");
                }
            }else {
                System.out.println("Usuario no encontrado");
            }

        }catch (Exception e){
            System.out.println("Error al loguear" + e);
        }

        return Optional.empty();
    }

    @Override
    public AuthResponse register(Usuario usuario) {
        AuthResponse            authResponse = new AuthResponse();
        DetailBody              detailBody;
        int index = 0;

        authResponse.responseActive();
        detailBody = new DetailBody();

        try {
            boolean emailExists = usuarioRepository.existsByEmail(usuario.getEmail());
            boolean userExists = usuario.getUser() != null && usuarioRepository.existsByUser(usuario.getUser());

            if (emailExists){
                authResponse.addFailed(authResponse.createErrorDetail("email", "El email ya se encuentra registrado", index++));
            }

            if (userExists){
                authResponse.addFailed(authResponse.createErrorDetail("user", "El nombre de usuario ya esta en uso", index++));
            }

            if (authResponse.getDetailDefaults() != null && !authResponse.getDetailDefaults().isEmpty()){
                return authResponse;
            }

            authResponse.addCorrect(detailBody);

            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioRepository.save(usuario);

        } catch (Exception e){
            System.out.println("Error al registrar" + e);
            authResponse.addFailed(detailBody, e);
        }

        return authResponse;
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return usuarioRepository.findById(id);
    }
}
