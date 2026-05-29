package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateAsignaturaCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteAsignaturaCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateAsignaturaCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetAsignaturaByIdQuery;
import com.jcaa.usersmanagement.domain.model.AsignaturaModel;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaAreaConocimiento;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaCarrera;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaContenidoTematico;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaDescripcion;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaId;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaNombre;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaNombreCompleto;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaNumeroCreditos;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaProfesor;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaSemestre;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AsignaturaApplicationMapper {

    public AsignaturaModel fromCreateCommandToModel(final CreateAsignaturaCommand command) {
        return AsignaturaModel.create(
                new AsignaturaId(command.id()),
                new AsignaturaNombre(command.nombre()),
                new AsignaturaNombreCompleto(command.nombreCompleto()),
                new AsignaturaDescripcion(command.descripcion()),
                new AsignaturaAreaConocimiento(command.areaConocimiento()),
                new AsignaturaCarrera(command.carrera()),
                new AsignaturaNumeroCreditos(command.numeroCreditos()),
                new AsignaturaContenidoTematico(command.contenidoTematico()),
                new AsignaturaSemestre(command.semestre()),
                new AsignaturaProfesor(command.profesor()));
    }

    public AsignaturaModel fromUpdateCommandToModel(final UpdateAsignaturaCommand command) {
        return new AsignaturaModel(
                new AsignaturaId(command.id()),
                new AsignaturaNombre(command.nombre()),
                new AsignaturaNombreCompleto(command.nombreCompleto()),
                new AsignaturaDescripcion(command.descripcion()),
                new AsignaturaAreaConocimiento(command.areaConocimiento()),
                new AsignaturaCarrera(command.carrera()),
                new AsignaturaNumeroCreditos(command.numeroCreditos()),
                new AsignaturaContenidoTematico(command.contenidoTematico()),
                new AsignaturaSemestre(command.semestre()),
                new AsignaturaProfesor(command.profesor()));
    }

    public AsignaturaId fromDeleteCommandToAsignaturaId(final DeleteAsignaturaCommand command) {
        return new AsignaturaId(command.id());
    }

    public AsignaturaId fromGetAsignaturaByIdQueryToAsignaturaId(
            final GetAsignaturaByIdQuery query) {
        return new AsignaturaId(query.id());
    }
}
