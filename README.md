# 🐾 AdoptApp - Backend

Backend desarrollado con Spring Boot para la plataforma AdoptApp.

## Tecnologías

- Java 21
- Spring Boot
- Spring Security
- PostgreSQL
- JWT
- WebSocket
- AWS (Despliegue)

## Requisitos

- JDK 21
- Maven

## Ejecución

```bash
git clone <URL_DEL_REPOSITORIO>
cd AdoptApp-Backend

mvn clean install

mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

La aplicación estará disponible en:

http://localhost:8082

## Entidades principales

- Users: Gestiona la información de los usuarios registrados en la plataforma.
- Hostels: Almacena los datos de los albergues que participan en el proceso de adopción.
- Pets: Registra la información de las mascotas disponibles para adopción.
- Adoptions: Gestiona los procesos de adopción realizados por los usuarios.

## Autores
- Caro Rojas, Dayanara Marlene
- Ferrel Julca, Rufo Piero
- Maza Lozada, Alexis Jair
- Rupay Huancachoque, Sergio Andre
- Valenzuela Cisneros, Kreisy Giomaira


Equipo de desarrollo AdoptApp.
