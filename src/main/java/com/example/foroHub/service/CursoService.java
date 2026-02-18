package com.example.foroHub.service;

import com.example.foroHub.DTO.CursoDTO.CursoActualizarDTO;
import com.example.foroHub.DTO.CursoDTO.CursoCrearDTO;
import com.example.foroHub.DTO.CursoDTO.CursoResponseDTO;
import com.example.foroHub.exception.BusinessException;
import com.example.foroHub.exception.NotFoundException;
import com.example.foroHub.model.Curso;
import com.example.foroHub.repository.CursoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CursoService {
    @Autowired
    private  CursoRepository cursoRepository;


    @Transactional
    public CursoResponseDTO crear(CursoCrearDTO dto) {
        if (cursoRepository.existsByNombre(dto.nombre())) {
            throw new BusinessException("Ya existe un curso con nombre: " + dto.nombre());
        }

        Curso c = Curso.builder()
                .nombre(dto.nombre())
                .categoria(dto.categoria())
                .build();

        return toResponse(cursoRepository.save(c));
    }

    public Page<CursoResponseDTO> listar(Pageable pageable) {
        return cursoRepository.findAll(pageable).map(this::toResponse);
    }

    public CursoResponseDTO detalle(Long id) {
        Curso c = cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso no encontrado: " + id));
        return toResponse(c);
    }

    @Transactional
    public CursoResponseDTO actualizar(Long id, CursoActualizarDTO dto) {
        Curso c = cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso no encontrado: " + id));

        if (dto.nombre() != null && !dto.nombre().equals(c.getNombre())) {
            if (cursoRepository.existsByNombre(dto.nombre())) {
                throw new BusinessException("Ya existe un curso con nombre: " + dto.nombre());
            }
            c.setNombre(dto.nombre());
        }

        if (dto.categoria() != null) c.setCategoria(dto.categoria());

        return toResponse(c);
    }

    @Transactional
    public void eliminar(Long id) {
        Curso c = cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso no encontrado: " + id));
        cursoRepository.delete(c);
    }

    private CursoResponseDTO toResponse(Curso c) {
        return new CursoResponseDTO(c.getId(), c.getNombre(), c.getCategoria());
    }
}
