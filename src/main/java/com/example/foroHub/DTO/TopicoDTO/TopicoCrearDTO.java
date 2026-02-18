package com.example.foroHub.DTO.TopicoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TopicoCrearDTO(
        @NotBlank @Size(min = 5, max = 200) String titulo,
        @NotBlank @Size(min = 10, max = 5000) String mensaje,
        @NotNull Long cursoId
) {
}
