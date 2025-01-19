package com.core.finanja.logueo.controller;

import com.core.finanja.config.security.TokenResponse;
import com.core.finanja.logueo.model.AuthResponse;
import com.core.finanja.logueo.model.DTO.LoginRequest;
import com.core.finanja.logueo.model.data.Usuario;
import com.core.finanja.logueo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){

        Map<String, Object> response;

        try {
            response = new HashMap<>();

            Optional<OAuth2AccessTokenResponse> tokenOptional = authService.login(
                    loginRequest.getEmailOrUsername(),
                    loginRequest.getPassword()
            );

            if (tokenOptional.isPresent()){
                OAuth2AccessTokenResponse tokenResponse = tokenOptional.get();
                response = TokenResponse.formatTokenResponse(tokenResponse);
                return ResponseEntity.ok().body(response);
            }else {
                response.put("success", false);
                response.put("msg", "Credenciales invalidas");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {

        Map<String, Object> response;
        AuthResponse responseBody;

        try{
            responseBody = this.authService.register(usuario);

            response = new HashMap<>();
            if (responseBody.getTotalDefaults() > 0){
                response.put("result", false);
                response.put("total_faileds", responseBody.getTotalDefaults() != null ? responseBody.getTotalDefaults() : 1);
                response.put("detail_faileds", responseBody.getDetailDefaults());
            }else {
                response.put("result", responseBody.getResult());
            }
        }catch(Exception e){
            response = new HashMap<>();
            response.put("result", false);
            response.put("detail_failed", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

}
