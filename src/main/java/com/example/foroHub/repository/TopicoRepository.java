package com.example.foroHub.repository;

import com.example.foroHub.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico,Long> {

    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    @EntityGraph(attributePaths = {"autor", "curso"})
    Page<Topico> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"autor", "curso"})
    Optional<Topico> findById(Long id);
}
