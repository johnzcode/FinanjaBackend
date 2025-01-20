package com.core.finanja.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class JwtSecurityConfig {

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {

        return (request, response, exception) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            Map<String, Object> errorDetails = new LinkedHashMap<>();
            String token = extractTokenFromRequest(request);

            errorDetails.put("error", "invalid_token");

            if (exception instanceof OAuth2AuthenticationException oAuth2Exception) {

                if (oAuth2Exception.getError().getDescription().contains("expired")) {
                    errorDetails.put("error_description", "Access token expired: " + token);
                } else if (exception.getCause() instanceof JwtException jwtException) {
                    errorDetails.put("error_description", "Cannot convert access token: " + jwtException.getMessage());
                } else {
                    errorDetails.put("error_description", "Malformed token: " + token);
                }
            } else {
                errorDetails.put("error_description", "Authentication failed: " + exception.getMessage());
            }

            String jsonError = new ObjectMapper().writeValueAsString(errorDetails);
            response.getWriter().write(jsonError);
        };
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        final String BEARER_PREFIX = "Bearer ";
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return "";
    }

}