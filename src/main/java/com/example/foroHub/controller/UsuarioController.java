package com.example.foroHub.controller;

import com.example.foroHub.DTO.UsuarioDTO.UsuarioActualizarDTO;
import com.example.foroHub.DTO.UsuarioDTO.UsuarioCrearDTO;
import com.example.foroHub.DTO.UsuarioDTO.UsuarioResponseDTO;
import com.example.foroHub.service.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {
    @Autowired
    private  UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@RequestBody @Valid UsuarioCrearDTO dto) {
        UsuarioResponseDTO creado = usuarioService.crear(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.id())
                .toUri();

        return ResponseEntity.created(location).body(creado);
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "fechaRegistro") Pageable pageable
    ) {
        return ResponseEntity.ok(usuarioService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> detalle(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.detalle(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioActualizarDTO dto
    ) {
        return ResponseEntity.ok(usuarioService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
