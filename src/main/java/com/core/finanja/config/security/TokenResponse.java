package com.core.finanja.config.security;

import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class TokenResponse {

    public static Map<String, Object> formatTokenResponse(OAuth2AccessTokenResponse tokenResponse) {
        Map<String, Object> response;

        response = new HashMap<>();

        OAuth2AccessToken accessToken = tokenResponse.getAccessToken();
        response.put("access_token", accessToken.getTokenValue());
        response.put("token_type", accessToken.getTokenType().getValue());
        response.put("expires_in", accessToken.getExpiresAt() != null
                ? (accessToken.getExpiresAt().toEpochMilli() - Objects.requireNonNull(accessToken.getIssuedAt()).toEpochMilli()) / 1000
                : null);
        assert tokenResponse.getRefreshToken() != null;
        response.put("refresh_token", tokenResponse.getRefreshToken().getTokenValue());
        response.put("scope", String.join(" ", accessToken.getScopes()));
        response.put("jti", UUID.randomUUID().toString());

        return response;
    }
}
