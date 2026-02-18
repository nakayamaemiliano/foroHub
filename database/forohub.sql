-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 18-02-2026 a las 22:58:19
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `forohub`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cursos`
--

CREATE TABLE `cursos` (
  `id` bigint(20) NOT NULL,
  `categoria` varchar(80) DEFAULT NULL,
  `nombre` varchar(120) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `cursos`
--

INSERT INTO `cursos` (`id`, `categoria`, `nombre`) VALUES
(2, 'Backend', 'Spring Boot'),
(3, 'Arquitecturasss', 'Docker y Deploy'),
(4, 'QA', 'Testing con Spring Boot'),
(5, 'skateboarding', 'skate');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flyway_schema_history`
--

CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `topicos`
--

CREATE TABLE `topicos` (
  `id` bigint(20) NOT NULL,
  `fecha_creacion` datetime(6) NOT NULL,
  `mensaje` tinytext NOT NULL,
  `status` enum('ABIERTO','CERRADO','RESUELTO') NOT NULL,
  `titulo` varchar(200) NOT NULL,
  `autor_id` bigint(20) NOT NULL,
  `curso_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `topicos`
--

INSERT INTO `topicos` (`id`, `fecha_creacion`, `mensaje`, `status`, `titulo`, `autor_id`, `curso_id`) VALUES
(1, '2026-02-17 13:32:31.000000', 'Resuelto: primero service unit tests, después controller con WebMvcTest.', 'RESUELTO', 'Error con Spring Security', 3, 2),
(2, '2026-02-18 19:14:55.000000', 'Quiero cubrir service con unit tests y controller con MockMvc. ¿Qué conviene primero?', 'ABIERTO', 'Cómo testear services y controllers', 4, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` bigint(20) NOT NULL,
  `email` varchar(150) NOT NULL,
  `fecha_registro` datetime(6) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `rol` enum('ADMIN','USUARIO') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `email`, `fecha_registro`, `nombre`, `password`, `rol`) VALUES
(3, 'emix@mail.com', '2026-02-17 13:26:15.000000', 'Emix', '$2a$10$DAF0fJgzjB7Xk0tWpZDUo.kLVvBwgQ0tLiIevZCOrOh7m6scDN3fC', 'USUARIO'),
(4, 'nico.test@forohub.com', '2026-02-17 14:49:52.000000', 'Nico Test', '$2a$10$d0OcC1giop4fmYt88TWSruOeTvaYYhobCPxM/BDaZKGsdfSxVMCAy', 'ADMIN');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cursos`
--
ALTER TABLE `cursos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK5j2ardatpnyxxtv783qm4vyyl` (`nombre`);

--
-- Indices de la tabla `flyway_schema_history`
--
ALTER TABLE `flyway_schema_history`
  ADD PRIMARY KEY (`installed_rank`),
  ADD KEY `flyway_schema_history_s_idx` (`success`);

--
-- Indices de la tabla `topicos`
--
ALTER TABLE `topicos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_topico_titulo_mensaje` (`titulo`,`mensaje`) USING HASH,
  ADD KEY `FKit6duktpmhkcetvx1ky3aakv8` (`autor_id`),
  ADD KEY `FKt8vtd2ay484ky6on2nygu9g3g` (`curso_id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKkfsp0s1tflm1cwlj8idhqsad0` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cursos`
--
ALTER TABLE `cursos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `topicos`
--
ALTER TABLE `topicos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `topicos`
--
ALTER TABLE `topicos`
  ADD CONSTRAINT `FKit6duktpmhkcetvx1ky3aakv8` FOREIGN KEY (`autor_id`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `FKt8vtd2ay484ky6on2nygu9g3g` FOREIGN KEY (`curso_id`) REFERENCES `cursos` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
