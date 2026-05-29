package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateAsignaturaCommand(
        @NotBlank(message = "id must not be blank") String id,
        @NotBlank(message = "nombre must not be blank")
        @Size(min = 3, message = "nombre must have at least 3 characters")
        String nombre,
        @NotBlank(message = "nombreCompleto must not be blank")
        @Size(min = 5, message = "nombreCompleto must have at least 5 characters")
        String nombreCompleto,
        @NotBlank(message = "descripcion must not be blank")
        @Size(min = 10, message = "descripcion must have at least 10 characters")
        String descripcion,
        @NotBlank(message = "areaConocimiento must not be blank") String areaConocimiento,
        @NotBlank(message = "carrera must not be blank")
        @Size(min = 3, message = "carrera must have at least 3 characters")
        String carrera,
        @Positive(message = "numeroCreditos must be positive") int numeroCreditos,
        @NotBlank(message = "contenidoTematico must not be blank")
        @Size(min = 10, message = "contenidoTematico must have at least 10 characters")
        String contenidoTematico,
        @Min(value = 1, message = "semestre must be greater than or equal to 1")
        @Max(value = 12, message = "semestre must be less than or equal to 12")
        int semestre,
        @NotBlank(message = "profesor must not be blank")
        @Size(min = 3, message = "profesor must have at least 3 characters")
        String profesor) {
}
