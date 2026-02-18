package com.example.foroHub.DTO.authDTO;

import java.time.Instant;

public record TokenResponse(
        String token,
        Instant expiresAt
) {
}
