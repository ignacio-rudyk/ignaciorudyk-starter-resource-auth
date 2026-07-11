# ignaciorudyk-starter-resource-auth

`ignaciorudyk-starter-resource-auth` es un **Spring Boot Starter** que centraliza la configuración de autorización basada en JWT para aplicaciones desarrolladas con Spring Boot y Spring Security.

Su objetivo es evitar duplicar la configuración de seguridad en cada microservicio, proporcionando una implementación reutilizable para la validación de tokens JWT y la protección de endpoints.

## Características

* Validación de tokens JWT.
* Configuración automática de Spring Security.
* Integración sencilla mediante Maven.
* Configuración de reglas de acceso desde `application.properties`.
* Compatible con Spring Boot 3.x y Java 21.

## Requisitos

* Java 21
* Spring Boot 3.3+
* Maven 3.9+

## Instalación

Agregar la dependencia al proyecto consumidor:

```xml
<dependency>
    <groupId>com.ignaciorudyk.resource.authorization</groupId>
    <artifactId>ignaciorudyk-starter-resource-auth</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Configuración

El starter permite definir los endpoints y sus reglas de acceso desde la configuración de la aplicación.

### Propiedades configurables en el proyecto consumidor

| Propiedad                                           | Descripción                                                                          | Obligatorio |
|-----------------------------------------------------|--------------------------------------------------------------------------------------|-------------|
| ignaciorudyk.authentication.enabled                 | Habilita o deshabilita la dependencia (por defecto es true).                         | No          |
| ignaciorudyk.authentication.secret-key              | Secret key.                                                                          | Sí          |
| ignaciorudyk.authentication.public-endpoints[index] | Configure un metodo público. Ej: ...public-method[0]=/login                          | No          |
| ignaciorudyk.authentication.user-endpoints[index]   | Configure un metodo con rol de user. Ej: ...authentication.user-method[0]=/user/**   | No          |
| ignaciorudyk.authentication.admin-endpoints[index]  | Configure un metodo con rol de admin. Ej: ...authentication.admin-method[0]=/user/** | No          |
| ignaciorudyk.authentication.allowed-origins[index]  | Configure qué dominios pueden consumir tu API.                                       | No          |
| ignaciorudyk.authentication.allowed-methods[index]  | Configure qué métodos HTTP están permitidos desde otros orígenes.                    | No          |
| ignaciorudyk.authentication.allowed-headers[index]  | Configure qué encabezados (headers) puede enviar el navegador al backend.            | No          |
| ignaciorudyk.authentication.exposed-headers[index]  | Configure qué headers de la respuesta el navegador puede leer desde JavaScript.      | No          |

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
