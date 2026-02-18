package com.example.foroHub.DTO.TopicoDTO;

import com.example.foroHub.model.StatusTopico;

import java.time.LocalDateTime;

public record TopicoResponseDTO(Long id,
                                String titulo,
                                String mensaje,
                                LocalDateTime fechaCreacion,
                                StatusTopico status,
                                String autorNombre,
                                String autorEmail,
                                String cursoNombre) {
}
