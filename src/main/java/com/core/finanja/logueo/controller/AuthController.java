package com.core.finanja.logueo.controller;

import com.core.finanja.logueo.model.DTO.LoginRequest;
import com.core.finanja.logueo.model.data.Usuario;
import com.core.finanja.logueo.service.AuthService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario){
        try{
            Usuario registerUser = authService.register(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        JSONObject response = null;
        try {
            response = new JSONObject();

            Optional<String> tokenOptional = authService.login(loginRequest.getEmailOrUsername(), loginRequest.getPassword());

            if (tokenOptional.isPresent()){
                response.put("Bearer", tokenOptional.get());
                return ResponseEntity.ok().body(response.toString());
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales invalidas");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }

    }
}
