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
