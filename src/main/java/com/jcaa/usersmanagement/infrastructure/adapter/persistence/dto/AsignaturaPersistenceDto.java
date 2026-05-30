package com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto;

public record AsignaturaPersistenceDto(
        String id,
        String nombre,
        String nombreCompleto,
        String descripcion,
        String areaConocimiento,
        String carrera,
        int numeroCreditos,
        String contenidoTematico,
        int semestre,
        String profesor,
        String createdAt,
        String updatedAt) {}
