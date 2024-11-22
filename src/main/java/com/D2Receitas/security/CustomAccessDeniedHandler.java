package com.D2Receitas.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                      AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String role = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");

            String redirectUrl = switch (role) {
                case "ROLE_ADMINISTRADOR" -> "/dashboard/administrador";
                case "ROLE_COZINHEIRO" -> "/dashboard/cozinheiro";
                case "ROLE_DEGUSTADOR" -> "/dashboard/degustador";
                case "ROLE_EDITOR" -> "/dashboard/editor";
                default -> "/dashboard";
            };

            response.sendRedirect(redirectUrl);
        }
    }
} 