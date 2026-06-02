package com.jcaa.usersmanagement.infrastructure.config;

import com.jcaa.usersmanagement.application.port.in.CreateAsignaturaUseCase;
import com.jcaa.usersmanagement.application.port.in.CreateUserUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteAsignaturaUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteUserUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllAsignaturasUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllUsersUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAsignaturaByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.GetUserByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.LoginUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateAsignaturaUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateUserUseCase;
import com.jcaa.usersmanagement.application.service.CreateAsignaturaService;
import com.jcaa.usersmanagement.application.service.CreateUserService;
import com.jcaa.usersmanagement.application.service.DeleteAsignaturaService;
import com.jcaa.usersmanagement.application.service.DeleteUserService;
import com.jcaa.usersmanagement.application.service.EmailNotificationService;
import com.jcaa.usersmanagement.application.service.GetAllAsignaturasService;
import com.jcaa.usersmanagement.application.service.GetAllUsersService;
import com.jcaa.usersmanagement.application.service.GetAsignaturaByIdService;
import com.jcaa.usersmanagement.application.service.GetUserByIdService;
import com.jcaa.usersmanagement.application.service.LoginService;
import com.jcaa.usersmanagement.application.service.UpdateAsignaturaService;
import com.jcaa.usersmanagement.application.service.UpdateUserService;
import com.jcaa.usersmanagement.infrastructure.adapter.email.JavaMailEmailSenderAdapter;
import com.jcaa.usersmanagement.infrastructure.adapter.email.SmtpConfig;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.config.DatabaseConfig;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.config.DatabaseConnectionFactory;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository.AsignaturaRepositoryMySQL;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository.UserRepositoryMySQL;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller.AsignaturaRestController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller.UserRestController;
import jakarta.validation.Validator;
import java.sql.Connection;

public final class DependencyContainer {

  private static final String DB_HOST     = "db.host";
  private static final String DB_PORT     = "db.port";
  private static final String DB_NAME     = "db.name";
  private static final String DB_USER     = "db.username";
  private static final String DB_PASSWORD = "db.password";

  private static final String SMTP_HOST      = "smtp.host";
  private static final String SMTP_PORT      = "smtp.port";
  private static final String SMTP_USER      = "smtp.username";
  private static final String SMTP_PASSWORD  = "smtp.password";
  private static final String SMTP_FROM      = "smtp.from.address";
  private static final String SMTP_FROM_NAME = "smtp.from.name";

  private final UserRestController userRestController;
  private final AsignaturaRestController asignaturaRestController;

  public DependencyContainer() {
    final AppProperties properties = new AppProperties();
    final Validator validator = ValidatorProvider.buildValidator();

    final Connection connection = buildDatabaseConnection(properties);
    final UserRepositoryMySQL userRepository = new UserRepositoryMySQL(connection);
    final AsignaturaRepositoryMySQL asignaturaRepository = new AsignaturaRepositoryMySQL(connection);

    final JavaMailEmailSenderAdapter emailSender =
            new JavaMailEmailSenderAdapter(buildSmtpConfig(properties));
    final EmailNotificationService emailNotification = new EmailNotificationService(emailSender);

    final CreateUserUseCase createUserUseCase =
            new CreateUserService(userRepository, userRepository, emailNotification, validator);
    final UpdateUserUseCase updateUserUseCase =
            new UpdateUserService(userRepository, userRepository, userRepository, emailNotification, validator);
    final DeleteUserUseCase deleteUserUseCase =
            new DeleteUserService(userRepository, userRepository, validator);
    final GetUserByIdUseCase getUserByIdUseCase =
            new GetUserByIdService(userRepository, validator);
    final GetAllUsersUseCase getAllUsersUseCase =
            new GetAllUsersService(userRepository);
    final LoginUseCase loginUseCase =
            new LoginService(userRepository, validator);

    final CreateAsignaturaUseCase createAsignaturaUseCase =
            new CreateAsignaturaService(asignaturaRepository, asignaturaRepository, validator);
    final UpdateAsignaturaUseCase updateAsignaturaUseCase =
            new UpdateAsignaturaService(asignaturaRepository, asignaturaRepository, asignaturaRepository, validator);
    final DeleteAsignaturaUseCase deleteAsignaturaUseCase =
            new DeleteAsignaturaService(asignaturaRepository, asignaturaRepository, validator);
    final GetAsignaturaByIdUseCase getAsignaturaByIdUseCase =
            new GetAsignaturaByIdService(asignaturaRepository, validator);
    final GetAllAsignaturasUseCase getAllAsignaturasUseCase =
            new GetAllAsignaturasService(asignaturaRepository);

    this.userRestController = new UserRestController(
            createUserUseCase,
            updateUserUseCase,
            deleteUserUseCase,
            getUserByIdUseCase,
            getAllUsersUseCase,
            loginUseCase);

    this.asignaturaRestController = new AsignaturaRestController(
            createAsignaturaUseCase,
            updateAsignaturaUseCase,
            deleteAsignaturaUseCase,
            getAsignaturaByIdUseCase,
            getAllAsignaturasUseCase);
  }

  public UserRestController userRestController() {
    return userRestController;
  }

  public AsignaturaRestController asignaturaRestController() {
    return asignaturaRestController;
  }

  private static Connection buildDatabaseConnection(final AppProperties properties) {
    final DatabaseConfig config = new DatabaseConfig(
            properties.get(DB_HOST),
            properties.getInt(DB_PORT),
            properties.get(DB_NAME),
            properties.get(DB_USER),
            properties.get(DB_PASSWORD));
    return DatabaseConnectionFactory.createConnection(config);
  }

  private static SmtpConfig buildSmtpConfig(final AppProperties properties) {
    return new SmtpConfig(
            properties.get(SMTP_HOST),
            properties.getInt(SMTP_PORT),
            properties.get(SMTP_USER),
            properties.get(SMTP_PASSWORD),
            properties.get(SMTP_FROM),
            properties.get(SMTP_FROM_NAME));
  }
}