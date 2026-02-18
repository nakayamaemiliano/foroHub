package com.example.foroHub.DTO.UsuarioDTO;

import com.example.foroHub.model.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UsuarioActualizarDTO(@Size(min = 2, max = 100) String nombre,
                                   @Email @Size(max = 150) String email,
                                   @Size(min = 6, max = 60) String password,
                                   Rol rol) {
}
