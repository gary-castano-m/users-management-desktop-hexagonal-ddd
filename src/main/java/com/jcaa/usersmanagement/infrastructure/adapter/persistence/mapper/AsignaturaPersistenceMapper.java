package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

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
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.AsignaturaPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.AsignaturaEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AsignaturaPersistenceMapper {

    public AsignaturaPersistenceDto fromModelToDto(final AsignaturaModel asignatura) {
        return new AsignaturaPersistenceDto(
                asignatura.getId().value(),
                asignatura.getNombre().value(),
                asignatura.getNombreCompleto().value(),
                asignatura.getDescripcion().value(),
                asignatura.getAreaConocimiento().value(),
                asignatura.getCarrera().value(),
                asignatura.getNumeroCreditos().value(),
                asignatura.getContenidoTematico().value(),
                asignatura.getSemestre().value(),
                asignatura.getProfesor().value(),
                null,
                null);
    }

    public AsignaturaEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
        return new AsignaturaEntity(
                resultSet.getString("id"),
                resultSet.getString("nombre"),
                resultSet.getString("nombre_completo"),
                resultSet.getString("descripcion"),
                resultSet.getString("area_conocimiento"),
                resultSet.getString("carrera"),
                resultSet.getInt("numero_creditos"),
                resultSet.getString("contenido_tematico"),
                resultSet.getInt("semestre"),
                resultSet.getString("profesor"),
                resultSet.getString("created_at"),
                resultSet.getString("updated_at"));
    }

    public AsignaturaModel fromEntityToModel(final AsignaturaEntity entity) {
        return new AsignaturaModel(
                new AsignaturaId(entity.id()),
                new AsignaturaNombre(entity.nombre()),
                new AsignaturaNombreCompleto(entity.nombreCompleto()),
                new AsignaturaDescripcion(entity.descripcion()),
                new AsignaturaAreaConocimiento(entity.areaConocimiento()),
                new AsignaturaCarrera(entity.carrera()),
                new AsignaturaNumeroCreditos(entity.numeroCreditos()),
                new AsignaturaContenidoTematico(entity.contenidoTematico()),
                new AsignaturaSemestre(entity.semestre()),
                new AsignaturaProfesor(entity.profesor()));
    }

    public AsignaturaModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
        return fromEntityToModel(fromResultSetToEntity(resultSet));
    }

    public List<AsignaturaModel> fromResultSetToModelList(final ResultSet resultSet)
            throws SQLException {
        final List<AsignaturaModel> asignaturas = new ArrayList<>();
        while (resultSet.next()) {
            asignaturas.add(fromResultSetToModel(resultSet));
        }
        return asignaturas;
    }
}
