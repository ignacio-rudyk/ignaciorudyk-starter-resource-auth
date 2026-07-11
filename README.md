# ignaciorudyk-starter-resource-auth

`ignaciorudyk-starter-resource-auth` es un **Spring Boot Starter** que centraliza la configuración de autorización basada en JWT para aplicaciones desarrolladas con Spring Boot y Spring Security.

Su objetivo es evitar duplicar la configuración de seguridad en cada microservicio, proporcionando una implementación reutilizable para la validación de tokens JWT y la protección de endpoints.

## Características

* Validación de tokens JWT.
* Configuración automática de Spring Security.
* Integración sencilla mediante Maven.
* Configuración de reglas de acceso desde `application.yml` o `application.properties`.
* Compatible con Spring Boot 3.x y Java 21.

## Requisitos

* Java 21
* Spring Boot 3.3+
* Maven 3.9+

## Instalación

Agregar la dependencia al proyecto consumidor:

```xml
<dependency>
    <groupId>com.ignaciorudyk.resource.auth</groupId>
    <artifactId>ignaciorudyk-starter-resource-auth</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Configuración

El starter permite definir los endpoints y sus reglas de acceso desde la configuración de la aplicación.

### Propiedades configurables en el proyecto consumidor

| Propiedad                                            | Descripción                                                                          | Obligatorio |
|------------------------------------------------------|--------------------------------------------------------------------------------------|-------------|
| ignaciorudyk.authentication.enabled                  | Habilita o deshabilita la dependencia (por defecto es true).                         | No          |
| ignaciorudyk.authentication.secret-key               | Secret key.                                                                          | Sí          |
| ignaciorudyk.authentication.access-token-expiration  | Define el access token expiration en MS.                                             | No          |
| ignaciorudyk.authentication.refresh-token-expiration | Define el refresh token expiration en MS.                                            | No          |
| ignaciorudyk.authentication.public-method[index]     | Configure un metodo público. Ej: ...public-method[0]=/login                          | No          |
| ignaciorudyk.authentication.user-method[index]       | Configure un metodo con rol de user. Ej: ...authentication.user-method[0]=/user/**   | No          |
| ignaciorudyk.authentication.admin-method[index]      | Configure un metodo con rol de admin. Ej: ...authentication.admin-method[0]=/user/** | No          |
| spring.datasource.url                                | Url de la base de datos del consumidor de la dependencia.                            | Sí          |
| spring.datasource.username                           | Usuario de la base de datos.                                                         | Sí          |
| spring.datasource.password                           | Contraseña de la base de datos.                                                      | Sí          |
| spring.datasource.driver-class-name                  | Indica qué driver JDBC debe utilizar Spring para conectarse a la base de datos.      | Sí          |
| spring.jpa.properties.hibernate.dialect              | Indica qué tipo de SQL generar para la base de datos.                                | Sí          |
| spring.flyway.enabled                                | Habilita o deshabilita la dependencia de Flyway.                                     | No          |
| spring.flyway.locations                              | Indica la ruta dónde debe buscar Flyway los scripts SQL de migración.                | Sí          |
| spring.flyway.baseline-on-migrate                    | Toma la instancia de la base de datos que fue creada sin flayway como punto inicial. | No          |
| springdoc.api-docs.path                              | Cambia la URL donde Springdoc expone el documento OpenAPI en formato JSON.           | No          |
| springdoc.swagger-ui.path                            | Permite cambiar la URL donde se muestra la interfaz de Swagger UI.                   | No          |
| springdoc.swagger-ui.tags-sorter                     | Define cómo se ordenan los tags en Swagger UI.                                       | No          |

## Funcionamiento

El starter registra automáticamente la configuración de Spring Security y:

* Valida el token JWT recibido en cada petición protegida.
* Permite el acceso a los endpoints públicos configurados.
* Restringe el acceso al resto de los endpoints según las reglas definidas.
* Deja la lógica de negocio completamente desacoplada de la configuración de seguridad.

## Casos de uso

Este starter está pensado para ecosistemas de microservicios donde múltiples aplicaciones comparten el mismo mecanismo de autorización basado en JWT.

## Estado del proyecto

🚧 Proyecto en desarrollo.

La documentación y las opciones de configuración se irán ampliando conforme evolucione el starter.
