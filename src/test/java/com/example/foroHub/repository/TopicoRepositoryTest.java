package com.example.foroHub.repository;

import com.example.foroHub.model.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.AutoConfigureDataJpa;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TopicoRepositoryTest {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("findById debe cargar autor y curso (EntityGraph)")
    void findById_debeCargarAutorYCurso() {
        Usuario autor = new Usuario();
        autor.setNombre("Autor Test");
        autor.setEmail("autor@test.com");
        autor.setPassword("hash");
        autor.setRol(Rol.USUARIO);
        em.persist(autor);

        Curso curso = new Curso();
        curso.setNombre("Spring Boot");
        curso.setCategoria("Backend");
        em.persist(curso);

        Topico t = new Topico();
        t.setTitulo("Titulo");
        t.setMensaje("Mensaje");
        t.setStatus(StatusTopico.ABIERTO); // ajustá al enum real
        t.setAutor(autor);
        t.setCurso(curso);
        // si fechaCreacion se setea con @PrePersist, no hace falta setearla acá
        em.persist(t);

        em.flush();
        em.clear();

        // Act
        Topico encontrado = topicoRepository.findById(t.getId())
                .orElseThrow();

        // Assert (sin depender de lazy)
        assertThat(encontrado.getAutor()).isNotNull();
        assertThat(encontrado.getCurso()).isNotNull();

        // chequeo “fuerte” (Hibernate)
        assertThat(org.hibernate.Hibernate.isInitialized(encontrado.getAutor())).isTrue();
        assertThat(org.hibernate.Hibernate.isInitialized(encontrado.getCurso())).isTrue();

        // extra: acceder a campos para confirmar que no hay LazyInitializationException
        assertThat(encontrado.getAutor().getEmail()).isEqualTo("autor@test.com");
        assertThat(encontrado.getCurso().getNombre()).isEqualTo("Spring Boot");
    }
}