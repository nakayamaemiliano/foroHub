package com.example.foroHub.controller;

import com.example.foroHub.DTO.CursoDTO.CursoActualizarDTO;
import com.example.foroHub.DTO.CursoDTO.CursoCrearDTO;
import com.example.foroHub.DTO.CursoDTO.CursoResponseDTO;
import com.example.foroHub.service.CursoService;
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
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {
    @Autowired
    private  CursoService cursoService;

    @PostMapping
    public ResponseEntity<CursoResponseDTO> crear(@RequestBody @Valid CursoCrearDTO dto) {
        CursoResponseDTO creado = cursoService.crear(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.id())
                .toUri();

        return ResponseEntity.created(location).body(creado);
    }

    @GetMapping
    public ResponseEntity<Page<CursoResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "nombre") Pageable pageable
    ) {
        return ResponseEntity.ok(cursoService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> detalle(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.detalle(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody @Valid CursoActualizarDTO dto
    ) {
        return ResponseEntity.ok(cursoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
