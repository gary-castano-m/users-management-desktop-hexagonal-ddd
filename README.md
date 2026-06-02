# Users Management REST API - Hexagonal Architecture + DDD

Aplicacion Java para la gestion de usuarios y asignaturas, construida con arquitectura hexagonal, enfoque DDD y una API REST expuesta con Javalin.

El proyecto separa el negocio de la infraestructura: el dominio contiene las reglas principales, la capa de aplicacion coordina los casos de uso y la infraestructura conecta la API REST, MySQL, correo SMTP y configuracion externa.

## Tecnologias

- Java 17
- Maven
- Javalin
- Jackson Databind
- MySQL Connector/J
- Jakarta Validation
- Hibernate Validator
- Lombok
- JavaMail
- BCrypt
- JUnit 5
- Mockito
- JaCoCo

## Arquitectura

El proyecto sigue una arquitectura hexagonal organizada en tres capas principales:

```text
src/main/java/com/jcaa/usersmanagement
├── domain
├── application
└── infrastructure
```

### Domain

Contiene el nucleo del negocio. Aquí viven los modelos, value objects, excepciones, enums y eventos de dominio.

Archivos representativos:

```text
domain/model/UserModel.java
domain/model/AsignaturaModel.java
domain/valueobject
domain/exception
domain/event
domain/enums
```

### Application

Contiene los casos de uso y los puertos. Define lo que la aplicación puede hacer sin depender de detalles como HTTP o MySQL.

Archivos representativos:

```text
application/port/in
application/port/out
application/service
application/service/dto
application/service/mapper
```

### Infrastructure

Contiene los adaptadores externos: API REST, persistencia MySQL, email, configuracion y mappers de entrada/salida.

Archivos representativos:

```text
infrastructure/entrypoint/rest
infrastructure/adapter/persistence
infrastructure/adapter/email
infrastructure/config
```

## API REST

La API REST se levanta desde:

```text
src/main/java/com/jcaa/usersmanagement/Main.java
```

El servidor usa Javalin y corre por defecto en:

```text
http://localhost:8081
```

Los controladores REST registran las rutas principales:

```text
UserRestController.java
AsignaturaRestController.java
```

## Endpoints

### Usuarios

```text
POST   /api/users/login
POST   /api/users
GET    /api/users
GET    /api/users/{id}
PUT    /api/users/{id}
DELETE /api/users/{id}
```

### Asignaturas

```text
POST   /api/asignaturas
GET    /api/asignaturas
GET    /api/asignaturas/{id}
PUT    /api/asignaturas/{id}
DELETE /api/asignaturas/{id}
```

## Flujo De Una Peticion

Ejemplo: crear una asignatura.

```text
HTTP POST /api/asignaturas
        ↓
AsignaturaRestController
        ↓
CreateAsignaturaRestRequest
        ↓
AsignaturaRestMapper
        ↓
CreateAsignaturaCommand
        ↓
CreateAsignaturaUseCase
        ↓
CreateAsignaturaService
        ↓
SaveAsignaturaPort
        ↓
AsignaturaRepositoryMySQL
        ↓
MySQL
```

Este flujo permite que la capa de aplicacion no dependa directamente de Javalin ni de MySQL.

## Idempotencia

El proyecto aplica idempotencia principalmente en operaciones `PUT` y `DELETE`.

Una operacion idempotente es aquella que puede ejecutarse varias veces con la misma entrada y producir el mismo resultado esperado, sin efectos secundarios repetidos innecesarios.

Aplicacion en el proyecto:

- `UpdateUserService`: compara si los datos cambiaron antes de enviar notificaciones.
- `UpdateAsignaturaService`: si la asignatura no cambia, retorna el estado actual sin actualizar innecesariamente.
- `DeleteUserService`: si el usuario ya no existe, la operacion se considera exitosa.
- `DeleteAsignaturaService`: si la asignatura ya no existe, la operacion se considera exitosa.

Nota: la idempotencia se aplica a nivel de comportamiento del caso de uso. No se usa un header como `Idempotency-Key`.

## Base De Datos

El esquema SQL se encuentra en:

```text
src/main/resources/schema.sql
```

Incluye las tablas:

```text
users
asignaturas
```

La configuracion de conexion se define en:

```text
src/main/resources/application.properties
```

Ejemplo:

```properties
db.host=localhost
db.port=3306
db.name=crud_usuarios
db.username=root
db.password=tu_password
```

## Configuracion SMTP

El proyecto incluye envio de correos para operaciones relacionadas con usuarios.

Configuracion:

```properties
smtp.host=smtp.gmail.com
smtp.port=587
smtp.username=tu_correo@gmail.com
smtp.password=tu_app_password
smtp.from.address=tu_correo@gmail.com
smtp.from.name=Gestion de Usuarios
```

## Ejecutar El Proyecto

1. Crear la base de datos ejecutando el script:

```text
src/main/resources/schema.sql
```

2. Configurar credenciales en:

```text
src/main/resources/application.properties
```

3. Compilar el proyecto:

```bash
mvn clean compile
```

4. Ejecutar pruebas:

```bash
mvn test
```

5. Ejecutar la aplicacion:

```bash
mvn exec:java -Dexec.mainClass="com.jcaa.usersmanagement.Main"
```

Tambien puede ejecutarse desde el IDE usando la clase `Main`.

## Postman

El proyecto incluye una coleccion de Postman para probar los endpoints:

```text
postman/users-management-api.postman_collection.json
```

La coleccion contiene ejemplos para:

- Login
- Crear usuario
- Listar usuarios
- Consultar usuario por ID
- Actualizar usuario
- Eliminar usuario
- Crear asignatura
- Listar asignaturas
- Consultar asignatura por ID
- Actualizar asignatura
- Eliminar asignatura

## Pruebas

El proyecto usa JUnit 5 y Mockito para pruebas unitarias. JaCoCo esta configurado para generar reportes de cobertura durante la fase `verify`.

```bash
mvn verify
```

## Estructura Principal

```text
src
├── main
│   ├── java
│   │   └── com/jcaa/usersmanagement
│   │       ├── domain
│   │       ├── application
│   │       └── infrastructure
│   └── resources
│       ├── application.properties
│       ├── schema.sql
│       └── templates
└── test
    └── java
```

## Autor

Gary José Castaño Molina cod.7502420050

Ingeniería de Software

<p align="center">
  <img src="docs/images/img.png" alt="" width="250">
</p>

