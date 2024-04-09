package com.example.circadianrhythmsync.security;

import com.example.circadianrhythmsync.validators.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateRequest {
    @ValidPassword
    private String password;
}

