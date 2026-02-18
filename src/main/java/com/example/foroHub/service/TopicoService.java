package com.example.foroHub.service;

import com.example.foroHub.DTO.TopicoDTO.TopicoActualizarDTO;
import com.example.foroHub.DTO.TopicoDTO.TopicoCrearDTO;
import com.example.foroHub.DTO.TopicoDTO.TopicoResponseDTO;
import com.example.foroHub.exception.BusinessException;
import com.example.foroHub.exception.NotFoundException;
import com.example.foroHub.model.Curso;
import com.example.foroHub.model.Topico;
import com.example.foroHub.model.Usuario;
import com.example.foroHub.repository.CursoRepository;
import com.example.foroHub.repository.TopicoRepository;
import com.example.foroHub.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {

    @Autowired
    private  TopicoRepository topicoRepository;
    @Autowired
    private  UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;



    @Transactional
    public TopicoResponseDTO crear(TopicoCrearDTO dto, String email) {
        if (topicoRepository.existsByTituloAndMensaje(dto.titulo(), dto.mensaje())) {
            throw new BusinessException("Ya existe un tópico con el mismo título y mensaje.");
        }

        Usuario autor = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Autor no encontrado por email: " + email));

        Curso curso = cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new NotFoundException("Curso no encontrado: " + dto.cursoId()));

        Topico t = Topico.builder()
                .titulo(dto.titulo())
                .mensaje(dto.mensaje())
                .autor(autor)
                .curso(curso)
                .build();

        Topico guardado = topicoRepository.save(t);
        return toResponse(guardado);
    }

    public Page<TopicoResponseDTO> listar(Pageable pageable) {
        return topicoRepository.findAll(pageable).map(this::toResponse);
    }

    public TopicoResponseDTO detalle(Long id) {
        Topico t = topicoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tópico no encontrado: " + id));
        return toResponse(t);
    }

    @Transactional
    public TopicoResponseDTO actualizar(Long id, TopicoActualizarDTO dto) {
        Topico t = topicoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tópico no encontrado: " + id));

        if (dto.titulo() != null && !dto.titulo().isBlank()) t.setTitulo(dto.titulo());
        if (dto.mensaje() != null && !dto.mensaje().isBlank()) t.setMensaje(dto.mensaje());
        if (dto.status() != null) t.setStatus(dto.status());

        if (dto.cursoId() != null) {
            Curso curso = cursoRepository.findById(dto.cursoId())
                    .orElseThrow(() -> new NotFoundException("Curso no encontrado: " + dto.cursoId()));
            t.setCurso(curso);
        }

        // opcional: validar duplicado si cambió título/mensaje
        if (topicoRepository.existsByTituloAndMensaje(t.getTitulo(), t.getMensaje()) && !t.getId().equals(id)) {
            throw new BusinessException("Actualización inválida: quedaría duplicado (título+mensaje).");
        }

        return toResponse(t);
    }

    @Transactional
    public void eliminar(Long id) {
        Topico t = topicoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tópico no encontrado: " + id));
        topicoRepository.delete(t);
    }

    private TopicoResponseDTO toResponse(Topico t) {
        return new TopicoResponseDTO(
                t.getId(),
                t.getTitulo(),
                t.getMensaje(),
                t.getFechaCreacion(),
                t.getStatus(),
                t.getAutor().getNombre(),
                t.getAutor().getEmail(),
                t.getCurso().getNombre()
        );
    }
}
