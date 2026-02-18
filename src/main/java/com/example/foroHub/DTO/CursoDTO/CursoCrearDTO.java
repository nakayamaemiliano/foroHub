package com.example.foroHub.DTO.CursoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CursoCrearDTO(
        @NotBlank @Size(min = 2, max = 120) String nombre,
        @Size(max = 80) String categoria
) {
}
