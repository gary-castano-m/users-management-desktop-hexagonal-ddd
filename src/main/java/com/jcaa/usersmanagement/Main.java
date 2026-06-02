package com.jcaa.usersmanagement;

import com.jcaa.usersmanagement.infrastructure.config.DependencyContainer;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {

  private static final Logger log = LoggerFactory.getLogger(Main.class);
  private static final int PORT = 8081;

  public static void main(final String[] args) {
    log.info("Starting Users Management REST API...");

    final DependencyContainer container = new DependencyContainer();

    final Javalin app = Javalin.create(config ->
            config.bundledPlugins.enableCors(cors ->
                    cors.addRule(rule -> rule.anyHost())));

    container.userRestController().registerRoutes(app);
    container.asignaturaRestController().registerRoutes(app);

    app.start(PORT);

    log.info("REST API running on http://localhost:{}", PORT);
  }
}