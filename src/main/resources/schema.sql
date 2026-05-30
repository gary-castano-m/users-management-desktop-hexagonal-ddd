
CREATE DATABASE IF NOT EXISTS crud_usuarios
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE crud_usuarios;

CREATE TABLE IF NOT EXISTS users (
                                     id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        ENUM('ADMIN', 'MEMBER', 'REVIEWER') NOT NULL,
    status      ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'BLOCKED') NOT NULL DEFAULT 'PENDING',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS asignaturas (
                                           id                  VARCHAR(36)  NOT NULL PRIMARY KEY,
    nombre              VARCHAR(100) NOT NULL UNIQUE,
    nombre_completo     VARCHAR(150) NOT NULL,
    descripcion         TEXT         NOT NULL,
    area_conocimiento   ENUM('HUMANIDADES', 'INGENIERIAS') NOT NULL,
    carrera             VARCHAR(100) NOT NULL,
    numero_creditos     INT          NOT NULL,
    contenido_tematico  TEXT         NOT NULL,
    semestre            INT          NOT NULL,
    profesor            VARCHAR(100) NOT NULL,
    created_at          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_asignaturas_numero_creditos_positive CHECK (numero_creditos > 0),
    CONSTRAINT chk_asignaturas_semestre_range CHECK (semestre BETWEEN 1 AND 12)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Usuario administrador inicial (password: Admin1234!)
INSERT INTO users (id, name, email, password, role, status)
VALUES (
           '00000000-0000-0000-0000-000000000001',
           'Administrador',
           'admin@example.com',
           '$2a$12$placeholderHashReplaceWithRealBCryptHash',
           'ADMIN',
           'ACTIVE'
       );
