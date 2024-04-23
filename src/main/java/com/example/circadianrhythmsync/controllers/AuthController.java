package com.example.circadianrhythmsync.controllers;

import com.example.circadianrhythmsync.security.AuthenticationService;
import com.example.circadianrhythmsync.security.AuthRequestDTO;
import com.example.circadianrhythmsync.security.RegisterReqDTO;
import com.example.circadianrhythmsync.security.JwtResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v${api-version}/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity register(
            @RequestBody RegisterReqDTO request) {
        service.register(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponseDTO> authenticate(
            @RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}