package com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception;

public final class PersistenceException extends RuntimeException {

  private static final String MESSAGE_SAVE = "Failed to save user with ID: '%s'.";
  private static final String MESSAGE_UPDATE = "Failed to update user with ID: '%s'.";
  private static final String MESSAGE_FIND = "Failed to find user with ID: '%s'.";
  private static final String MESSAGE_EMAIL = "Failed to find user with email: '%s'.";
  private static final String MESSAGE_ALL = "Failed to retrieve all users.";
  private static final String MESSAGE_DELETE = "Failed to delete user with ID: '%s'.";
  private static final String MESSAGE_SAVE_ASIGNATURA =
          "Failed to save asignatura with ID: '%s'.";
  private static final String MESSAGE_UPDATE_ASIGNATURA =
          "Failed to update asignatura with ID: '%s'.";
  private static final String MESSAGE_FIND_ASIGNATURA =
          "Failed to find asignatura with ID: '%s'.";
  private static final String MESSAGE_NOMBRE_ASIGNATURA =
          "Failed to find asignatura with nombre: '%s'.";
  private static final String MESSAGE_ALL_ASIGNATURAS = "Failed to retrieve all asignaturas.";
  private static final String MESSAGE_DELETE_ASIGNATURA =
          "Failed to delete asignatura with ID: '%s'.";
  private static final String MESSAGE_CONNECTION = "Could not establish database connection.";

  private PersistenceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static PersistenceException becauseSaveFailed(final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_SAVE, userId), cause);
  }

  public static PersistenceException becauseUpdateFailed(
          final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_UPDATE, userId), cause);
  }

  public static PersistenceException becauseFindByIdFailed(
          final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_FIND, userId), cause);
  }

  public static PersistenceException becauseFindByEmailFailed(
          final String email, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_EMAIL, email), cause);
  }

  public static PersistenceException becauseFindAllFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_ALL, cause);
  }

  public static PersistenceException becauseDeleteFailed(
          final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_DELETE, userId), cause);
  }

  public static PersistenceException becauseSaveAsignaturaFailed(
          final String asignaturaId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_SAVE_ASIGNATURA, asignaturaId), cause);
  }

  public static PersistenceException becauseUpdateAsignaturaFailed(
          final String asignaturaId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_UPDATE_ASIGNATURA, asignaturaId), cause);
  }

  public static PersistenceException becauseFindAsignaturaByIdFailed(
          final String asignaturaId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_FIND_ASIGNATURA, asignaturaId), cause);
  }

  public static PersistenceException becauseFindAsignaturaByNombreFailed(
          final String nombre, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_NOMBRE_ASIGNATURA, nombre), cause);
  }

  public static PersistenceException becauseFindAllAsignaturasFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_ALL_ASIGNATURAS, cause);
  }

  public static PersistenceException becauseDeleteAsignaturaFailed(
          final String asignaturaId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_DELETE_ASIGNATURA, asignaturaId), cause);
  }

  public static PersistenceException becauseConnectionFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_CONNECTION, cause);
  }
}