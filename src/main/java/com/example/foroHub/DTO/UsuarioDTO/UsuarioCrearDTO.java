package com.example.foroHub.DTO.UsuarioDTO;

import com.example.foroHub.model.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCrearDTO(@NotBlank @Size(min = 2, max = 100) String nombre,
                              @NotBlank @Email @Size(max = 150) String email,
                              @NotBlank @Size(min = 6, max = 60) String password,
                              Rol rol) {
}
