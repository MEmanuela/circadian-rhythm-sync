package com.example.circadianrhythmsync.controllers;

import com.example.circadianrhythmsync.security.PasswordUpdateRequest;
import com.example.circadianrhythmsync.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v${api-version}")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/{userId}/password")
    @PreAuthorize("#userId == principal.userId")
    public ResponseEntity updatePassword(@PathVariable(value = "userId") Long userId,
                                         @Valid @RequestBody PasswordUpdateRequest request) {
        userService.updateUserPassword(userId, request.getPassword());
        return new ResponseEntity(HttpStatus.OK);
    }
}
