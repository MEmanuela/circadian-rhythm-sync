package com.example.circadianrhythmsync.security;

import com.example.circadianrhythmsync.configuration.JwtService;
import com.example.circadianrhythmsync.entities.Token;
import com.example.circadianrhythmsync.entities.User;
import com.example.circadianrhythmsync.entities.Role;
import com.example.circadianrhythmsync.exceptions.UserAuthenticationException;
import com.example.circadianrhythmsync.repositories.TokenRepository;
import com.example.circadianrhythmsync.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private MessageSource messageSource;
    public JwtResponseDTO register(RegisterReqDTO request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(new Role(Long.valueOf(1), "admin"))
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return JwtResponseDTO.builder()
                .accessToken(jwtToken).build();
    }
    public JwtResponseDTO authenticate(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername()).get();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        var response = JwtResponseDTO.builder()
                .accessToken(jwtToken).build();
        if (response.getAccessToken() != null) {
            return response;
        } else {
            throw new UserAuthenticationException(messageSource.getMessage("exception.authenticationError", null, Locale.ENGLISH));
        }
    }
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }
    private void revokeAllUserTokens(User user) {
        var validUserToken =
                tokenRepository.findAllByIsExpiredOrIsRevokedAndUser_UserId(
                        false, false, user.getUserId());
        if (validUserToken.isEmpty()) return;
        validUserToken.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserToken);
    }
}