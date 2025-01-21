package com.core.finanja.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtils {

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    public JwtUtils(@Value("${encryption.key}") String privateKeyPath, @Value("${decryption.key}") String publicKeyPath) {
        try {

            this.privateKey = KeyUtil.getPrivateKey(privateKeyPath);
            this.publicKey = KeyUtil.getPublicKey(publicKeyPath);
        } catch (Exception e) {
            System.err.println("Error al cargar las claves: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static final long ACCESS_TOKEN_EXPIRATION = 3600; //1 hora
    private static final long REFRESH_TOKEN_EXPIRATION = 4500; //1 hora y 15

    public OAuth2AccessTokenResponse generateTokens(String username){
        String accessToken = generateToken(username, ACCESS_TOKEN_EXPIRATION);
        String refreshToken = generateToken(username, REFRESH_TOKEN_EXPIRATION);

        return OAuth2AccessTokenResponse
                .withToken(accessToken)
                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .expiresIn(ACCESS_TOKEN_EXPIRATION)
                .refreshToken(refreshToken)
                .scopes(Set.of("read", "write"))
                .build();
    }

    private String generateToken(String username, long expiration){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (expiration * 1000) ))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
}
