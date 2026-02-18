package com.example.foroHub.service;

import com.example.foroHub.DTO.UsuarioDTO.UsuarioActualizarDTO;
import com.example.foroHub.DTO.UsuarioDTO.UsuarioCrearDTO;
import com.example.foroHub.DTO.UsuarioDTO.UsuarioResponseDTO;
import com.example.foroHub.exception.BusinessException;
import com.example.foroHub.exception.NotFoundException;
import com.example.foroHub.model.Rol;
import com.example.foroHub.model.Usuario;
import com.example.foroHub.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public UsuarioResponseDTO crear(UsuarioCrearDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new BusinessException("Ya existe un usuario con email: " + dto.email());
        }

        Usuario u = Usuario.builder()
                .nombre(dto.nombre())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .rol(dto.rol() != null ? dto.rol() : Rol.USUARIO)
                .build();

        return toResponse(usuarioRepository.save(u));
    }

    public Page<UsuarioResponseDTO> listar(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(this::toResponse);
    }

    public UsuarioResponseDTO detalle(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + id));
        return toResponse(u);
    }

    @Transactional
    public UsuarioResponseDTO actualizar(Long id, UsuarioActualizarDTO dto) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + id));

        if (dto.email() != null && !dto.email().equals(u.getEmail())) {
            if (usuarioRepository.existsByEmail(dto.email())) {
                throw new BusinessException("Ya existe un usuario con email: " + dto.email());
            }
            u.setEmail(dto.email());
        }

        if (dto.nombre() != null && !dto.nombre().isBlank()) u.setNombre(dto.nombre());
        if (dto.rol() != null) u.setRol(dto.rol());

        if (dto.password() != null && !dto.password().isBlank()) {
            u.setPassword(passwordEncoder.encode(dto.password()));
        }

        return toResponse(u);
    }

    @Transactional
    public void eliminar(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + id));
        usuarioRepository.delete(u);
    }

    private UsuarioResponseDTO toResponse(Usuario u) {
        return new UsuarioResponseDTO(u.getId(), u.getNombre(), u.getEmail(), u.getRol(), u.getFechaRegistro());
    }
}
