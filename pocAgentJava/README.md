# pocAgentJava

POC independiente para validar el agente `agenteEspecializadoJavaSPHexV2` y servir como punto de partida para un arquetipo de microservicios Java. El proyecto implementa un CRUD REST de productos con Java 21, Spring Boot 4.1.1, arquitectura hexagonal y H2 en memoria. No modifica `mdw-poc-infra-ms`.

El modelo de ejecución es Spring MVC bloqueante con JPA. Las transacciones de escritura se delimitan en los servicios de aplicación; las lecturas son de solo lectura.

## Requisitos y ejecución

- JDK 21

En Windows, si vas a correrlo desde la terminal de IntelliJ, asegúrate de que `JAVA_HOME` apunte al JDK 21 y usa un home local de Maven para evitar el `settings.xml` bloqueado del usuario:

```powershell
$env:JAVA_HOME = 'C:\ruta\real\al\jdk-21'
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
$env:MAVEN_USER_HOME = 'C:\.m2-pocagentjava'
.\mvnw.cmd clean install
```

Si quieres arrancar la aplicación después de compilar, usa:

```powershell
.\mvnw.cmd spring-boot:run
```

Si prefieres no tocar `MAVEN_USER_HOME`, también puedes pasar un `settings.xml` propio:

```powershell
.\mvnw.cmd -s C:\ruta\a\settings.xml clean install
```

Si Maven falla al construir los *effective settings* por un `settings.xml` no legible en `C:\Users\cristianf.ramirez\.m2\settings.xml`, usa una de las opciones anteriores. La primera es la más simple cuando IntelliJ hereda ese perfil de usuario.

La API queda disponible en `http://localhost:8080`. La base H2 es efímera y se inicializa desde `src/main/resources/Database/Schema.sql`; Hibernate no crea ni modifica el esquema.

## API

Base: `/api/v1/products`

| Método | Ruta | Resultado |
| --- | --- | --- |
| `POST` | `/api/v1/products` | Crea producto; responde `201` y `Location` |
| `GET` | `/api/v1/products` | Lista productos |
| `GET` | `/api/v1/products/{id}` | Consulta uno; `404` si no existe |
| `PUT` | `/api/v1/products/{id}` | Actualiza producto; `404` si no existe |
| `DELETE` | `/api/v1/products/{id}` | Elimina; responde `204` o `404` |

Ejemplo de cuerpo para crear o actualizar:

```json
{
  "name": "Keyboard",
  "description": "Mechanical",
  "active": true
}
```

Los errores HTTP siguen el formato local GuardRails (`timestamp`, `status`, `error`, `code`, `message`, `path`, `details`). La API no tiene autenticación y está destinada exclusivamente a desarrollo local; no debe exponerse ni considerarse lista para producción.

## Verificación

```powershell
.\mvnw.cmd clean install
```

La suite cubre:

- Reglas y orquestación de casos de uso sin iniciar Spring.
- Persistencia JPA contra H2 y el esquema SQL del proyecto.
- Flujo REST completo de creación, lectura, listado, actualización y borrado, además de validación y not-found.
- Reglas ArchUnit para dependencias hacia adentro, aislamiento del dominio, acceso a persistencia, ausencia de field injection y ciclos.

## Estructura

- `domain`: producto, commands/queries, excepciones y puertos.
- `application`: servicios de casos de uso y límites transaccionales.
- `infrastructure`: adapters REST y JPA, DTOs, mappers y manejo HTTP de errores.
- `src/test`: pruebas unitarias, de integración y arquitectura.

Este POC es una base ilustrativa: seguridad, migraciones, observabilidad y decisiones específicas del dominio deben acordarse antes de derivar un servicio productivo o un arquetipo corporativo.
