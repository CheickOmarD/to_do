package com.technologia.to_do.controller;


import com.technologia.to_do.dto.AuthentificationRequest;
import com.technologia.to_do.dto.AuthentificationResponse;
import com.technologia.to_do.services.authentificaion.AuthentificationService;
import com.technologia.to_do.services.logout.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

    @CrossOrigin
    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/auth")
    public class AuthentificationController {

        private final AuthentificationService service;
        private final LogoutService logoutService;

        @PostMapping
        @ResponseStatus(HttpStatus.OK)
        AuthentificationResponse authenticate(@RequestBody AuthentificationRequest request) {
            return service.authenticate(request);
        }

        @GetMapping("/logout")
        @ResponseStatus(HttpStatus.OK)
        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            logoutService.logout(request, response, authentication);
    }
}
