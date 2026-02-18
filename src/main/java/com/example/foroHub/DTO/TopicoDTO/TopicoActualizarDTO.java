package com.example.foroHub.DTO.TopicoDTO;

import com.example.foroHub.model.StatusTopico;
import jakarta.validation.constraints.Size;

public record TopicoActualizarDTO(@Size(min = 5, max = 200) String titulo,
                                  @Size(min = 10, max = 5000) String mensaje,
                                  StatusTopico status,
                                  Long cursoId) {
}
