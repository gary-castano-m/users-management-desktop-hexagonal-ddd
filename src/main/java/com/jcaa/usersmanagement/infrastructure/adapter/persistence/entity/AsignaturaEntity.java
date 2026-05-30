package com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity;

public record AsignaturaEntity(
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
