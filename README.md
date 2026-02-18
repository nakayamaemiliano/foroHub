# ForoHub API (JWT) — Proyecto Final Alura (Foro Hub)

API REST desarrollada como **proyecto final del challenge “Foro Hub” (Alura)**.  
El objetivo es replicar el backend de un foro: gestión de **tópicos**, con persistencia en **MySQL**, validaciones de negocio y **seguridad con JWT (RS256 + RSA)**.

---

## Contenido
- [Funcionalidades](#funcionalidades)
- [Stack / Tecnologías](#stack--tecnologías)
- [Requisitos](#requisitos)
- [Configuración y ejecución](#configuración-y-ejecución)
  - [1) Base de datos (MySQL/XAMPP)](#1-base-de-datos-mysqlxampp)
  - [2) Properties](#2-properties)
  - [3) Generar llaves RSA (JWT RS256)](#3-generar-llaves-rsa-jwt-rs256)
  - [4) Levantar la app](#4-levantar-la-app)
- [Documentación (Swagger/OpenAPI)](#documentación-swaggeropenapi)
- [Postman](#postman)
- [Autenticación y roles](#autenticación-y-roles)
  - [Crear usuario y obtener token](#crear-usuario-y-obtener-token)
  - [Bootstrapping de ADMIN (obligatorio para probar cursos/usuarios)](#bootstrapping-de-admin-obligatorio-para-probar-cursosusuarios)
- [Endpoints](#endpoints)
- [Códigos de respuesta esperados](#códigos-de-respuesta-esperados)
- [Estructura del repositorio](#estructura-del-repositorio)
- [Notas y troubleshooting](#notas-y-troubleshooting)
- [Mejoras futuras](#mejoras-futuras)

---

## Funcionalidades

### Tópicos (CRUD)
- Crear tópico
- Listar tópicos
- Ver detalle de un tópico
- Actualizar tópico
- Eliminar tópico

### Extras para destacar (si están habilitados en tu proyecto)
- CRUD de **Cursos**
- CRUD de **Usuarios** (solo ADMIN)

### Seguridad
- Autenticación con **JWT** (firma RS256 usando RSA)
- Autorización por roles:
  - `ADMIN`: administra **cursos** y **usuarios**
  - `USUARIO`: accede a endpoints protegidos según configuración (por ejemplo, tópicos)

---

## Stack / Tecnologías
- Java + Spring Boot (v4)
- Spring Web
- Spring Data JPA / Hibernate
- MySQL (XAMPP)
- Spring Security + OAuth2 Resource Server (JWT)
- Swagger/OpenAPI (springdoc)
- Postman (colección exportada)

---

## Requisitos
- Java (la versión que use tu `pom.xml`)
- Maven
- MySQL + phpMyAdmin (XAMPP)
- (Opcional) Git

---

## Configuración y ejecución

### 1) Base de datos (MySQL/XAMPP)
1. Iniciar MySQL desde XAMPP.
2. Crear la base (si aplica):
   ```sql
   CREATE DATABASE forohub;
