# Finanja

## Descripción

FinanjaBackend es el backend de una aplicación de gestión financiera personal. 
Este proyecto proporciona una API RESTful desarrollada con Spring Boot para 
gestionar usuarios, categorías y presupuestos. Incluye autenticación 
segura mediante JWT y está diseñado para ser escalable y extensible.

## Tecnologías utilizadas

- Java 21
- Spring Boot
  - Spring Web
  - Spring Security
  - Spring Data JPA
- PostgreSQL
- Hibernate
- Lombok
- JWT
- Gradle

## Características principales

- Autenticación y autorización:
  - Registro de usuarios.
  - Inicio de sesión con tokens JWT.
  - Protección de rutas según roles.
- Gestor de transacciones:
  - CRUD para ingresos y gastos.
  - Relación entre usuarios, categorías y transacciones.
- Gestor de categorías:
  - Personalización de categorías por usuario.
- Reportes financieros:
  - Generación de reportes resumidos (mensuales/anuales).
- Escalabilidad:
  - Arquitectura modular para facilitar la adición de nuevas funcionalidades