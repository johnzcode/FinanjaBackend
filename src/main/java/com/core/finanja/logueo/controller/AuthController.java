package com.core.finanja.logueo.controller;

import com.core.finanja.logueo.model.AuthResponse;
import com.core.finanja.logueo.model.DTO.LoginRequest;
import com.core.finanja.logueo.model.data.Usuario;
import com.core.finanja.logueo.service.AuthService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) throws Exception{

        Map<String, Object> response = null;
        AuthResponse responseBody = null;

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
