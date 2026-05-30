package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeleteAsignaturaPort;
import com.jcaa.usersmanagement.application.port.out.GetAllAsignaturasPort;
import com.jcaa.usersmanagement.application.port.out.GetAsignaturaByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetAsignaturaByNombrePort;
import com.jcaa.usersmanagement.application.port.out.SaveAsignaturaPort;
import com.jcaa.usersmanagement.application.port.out.UpdateAsignaturaPort;
import com.jcaa.usersmanagement.domain.exception.AsignaturaNotFoundException;
import com.jcaa.usersmanagement.domain.model.AsignaturaModel;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaId;
import com.jcaa.usersmanagement.domain.valueobject.AsignaturaNombre;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.AsignaturaPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.AsignaturaPersistenceMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public final class AsignaturaRepositoryMySQL
        implements SaveAsignaturaPort,
        UpdateAsignaturaPort,
        GetAsignaturaByIdPort,
        GetAsignaturaByNombrePort,
        GetAllAsignaturasPort,
        DeleteAsignaturaPort {

    private static final String SELECT_COLUMNS =
            "id, nombre, nombre_completo, descripcion, area_conocimiento, carrera, "
                    + "numero_creditos, contenido_tematico, semestre, profesor, created_at, updated_at ";

    private static final String SQL_INSERT =
            "INSERT INTO asignaturas "
                    + "(id, nombre, nombre_completo, descripcion, area_conocimiento, carrera, "
                    + "numero_creditos, contenido_tematico, semestre, profesor, created_at, updated_at) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

    private static final String SQL_UPDATE =
            "UPDATE asignaturas SET nombre = ?, nombre_completo = ?, descripcion = ?, "
                    + "area_conocimiento = ?, carrera = ?, numero_creditos = ?, contenido_tematico = ?, "
                    + "semestre = ?, profesor = ?, updated_at = NOW() "
                    + "WHERE id = ?";

    private static final String SQL_SELECT_BY_ID =
            "SELECT " + SELECT_COLUMNS + "FROM asignaturas WHERE id = ? LIMIT 1";

    private static final String SQL_SELECT_BY_NOMBRE =
            "SELECT " + SELECT_COLUMNS + "FROM asignaturas WHERE nombre = ? LIMIT 1";

    private static final String SQL_SELECT_ALL =
            "SELECT " + SELECT_COLUMNS + "FROM asignaturas ORDER BY nombre ASC";

    private static final String SQL_DELETE = "DELETE FROM asignaturas WHERE id = ?";

    private final Connection connection;

    @Override
    public AsignaturaModel save(final AsignaturaModel asignatura) {
        final AsignaturaPersistenceDto dto = AsignaturaPersistenceMapper.fromModelToDto(asignatura);
        executeSave(dto);
        return findByIdOrFail(asignatura.getId());
    }

    @Override
    public AsignaturaModel update(final AsignaturaModel asignatura) {
        final AsignaturaPersistenceDto dto = AsignaturaPersistenceMapper.fromModelToDto(asignatura);
        executeUpdate(dto);
        return findByIdOrFail(asignatura.getId());
    }

    @Override
    public Optional<AsignaturaModel> getById(final AsignaturaId asignaturaId) {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setString(1, asignaturaId.value());
            final ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(AsignaturaPersistenceMapper.fromResultSetToModel(resultSet));
        } catch (final SQLException exception) {
            throw PersistenceException.becauseFindAsignaturaByIdFailed(asignaturaId.value(), exception);
        }
    }

    @Override
    public Optional<AsignaturaModel> getByNombre(final AsignaturaNombre nombre) {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_NOMBRE)) {
            statement.setString(1, nombre.value());
            final ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(AsignaturaPersistenceMapper.fromResultSetToModel(resultSet));
        } catch (final SQLException exception) {
            throw PersistenceException.becauseFindAsignaturaByNombreFailed(nombre.value(), exception);
        }
    }

    @Override
    public List<AsignaturaModel> getAll() {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
            final ResultSet resultSet = statement.executeQuery();
            return AsignaturaPersistenceMapper.fromResultSetToModelList(resultSet);
        } catch (final SQLException exception) {
            throw PersistenceException.becauseFindAllAsignaturasFailed(exception);
        }
    }

    @Override
    public void delete(final AsignaturaId asignaturaId) {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setString(1, asignaturaId.value());
            statement.executeUpdate();
        } catch (final SQLException exception) {
            throw PersistenceException.becauseDeleteAsignaturaFailed(asignaturaId.value(), exception);
        }
    }

    private void executeSave(final AsignaturaPersistenceDto dto) {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
            statement.setString(1, dto.id());
            statement.setString(2, dto.nombre());
            statement.setString(3, dto.nombreCompleto());
            statement.setString(4, dto.descripcion());
            statement.setString(5, dto.areaConocimiento());
            statement.setString(6, dto.carrera());
            statement.setInt(7, dto.numeroCreditos());
            statement.setString(8, dto.contenidoTematico());
            statement.setInt(9, dto.semestre());
            statement.setString(10, dto.profesor());
            statement.executeUpdate();
        } catch (final SQLException exception) {
            throw PersistenceException.becauseSaveAsignaturaFailed(dto.id(), exception);
        }
    }

    private void executeUpdate(final AsignaturaPersistenceDto dto) {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, dto.nombre());
            statement.setString(2, dto.nombreCompleto());
            statement.setString(3, dto.descripcion());
            statement.setString(4, dto.areaConocimiento());
            statement.setString(5, dto.carrera());
            statement.setInt(6, dto.numeroCreditos());
            statement.setString(7, dto.contenidoTematico());
            statement.setInt(8, dto.semestre());
            statement.setString(9, dto.profesor());
            statement.setString(10, dto.id());
            statement.executeUpdate();
        } catch (final SQLException exception) {
            throw PersistenceException.becauseUpdateAsignaturaFailed(dto.id(), exception);
        }
    }

    private AsignaturaModel findByIdOrFail(final AsignaturaId asignaturaId) {
        return getById(asignaturaId)
                .orElseThrow(
                        () -> AsignaturaNotFoundException.becauseIdWasNotFound(asignaturaId.value()));
    }
}
