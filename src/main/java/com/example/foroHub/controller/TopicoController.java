package com.example.foroHub.controller;

import com.example.foroHub.DTO.TopicoDTO.TopicoActualizarDTO;
import com.example.foroHub.DTO.TopicoDTO.TopicoCrearDTO;
import com.example.foroHub.DTO.TopicoDTO.TopicoResponseDTO;
import com.example.foroHub.service.TopicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
    @Autowired
    private  TopicoService topicoService;


    @PostMapping
    public ResponseEntity<TopicoResponseDTO> crear(@RequestBody @Valid TopicoCrearDTO dto, Authentication auth) {
        TopicoResponseDTO creado = topicoService.crear(dto, auth.getName());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.id())
                .toUri();

        return ResponseEntity.created(location).body(creado);
    }

    @GetMapping
    public ResponseEntity<Page<TopicoResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(topicoService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> detalle(@PathVariable Long id) {
        return ResponseEntity.ok(topicoService.detalle(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody @Valid TopicoActualizarDTO dto
    ) {
        return ResponseEntity.ok(topicoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        topicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
