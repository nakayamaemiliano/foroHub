package com.example.foroHub.DTO.UsuarioDTO;

import com.example.foroHub.model.Rol;

import java.time.LocalDateTime;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String email,
        Rol rol,
        LocalDateTime fechaRegistro
) {
}
