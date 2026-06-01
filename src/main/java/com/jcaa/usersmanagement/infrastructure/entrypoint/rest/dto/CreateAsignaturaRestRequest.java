package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto;

public record CreateAsignaturaRestRequest(
        String id,
        String nombre,
        String nombreCompleto,
        String descripcion,
        String areaConocimiento,
        String carrera,
        int numeroCreditos,
        String contenidoTematico,
        int semestre,
        String profesor) {}