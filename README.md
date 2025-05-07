![Descripción alternativa](src/main/resources/static/calculator-image.png)

# Spring Boot - Calculadora Aritmética

## Descripción
*Este proyecto es una API REST de operaciones matemáticas desarrollada con Spring Boot. Permite realizar cálculos, almacenar el historial de operaciones realizadas, consultar dicho historial con filtros por tipo de operación y rango de fechas, así como eliminar operaciones específicas.**

*Incluye integración con Swagger para facilitar la documentación y pruebas interactivas de los endpoints.**

## Características

1. **Autenticación con Spring Security**: La API implementa un medio y esquema de autenticación utilizando Spring Security. Esto permite identificar qué usuario está realizando búsquedas de información.

2. **Autorización con Json Web Token**: La API implementa un método de seguridad que permite validar y controlar el acceso a mi API REST.

3. **Arquitectura en capas**: Este proyecto implementa una arquitectura en capas claramente definida, siguiendo los principios de separación de responsabilidades y alta cohesión. Cada capa tiene una función específica y se comunica con las demás de manera controlada.

4. **Almacenamiento en Base de Datos**: Se implementa un esquema de base de datos, preferiblemente PostgreSQL, para almacenar toda la información necesaria. Esto incluye datos relacionados con operaciones aritméticas y usuarios.

5. **Documentación con Swagger**: Este proyecto utiliza Swagger para la generación automática de documentación de la API REST. Swagger permite explorar y probar los endpoints de forma interactiva desde una interfaz web intuitiva.

Puedes acceder a la documentación en:
```properties
http://localhost:8080/swagger-ui.html
```

## Configuración
## Instrucciones de Instalación

### Requisitos Previos

- Java JDK 17+
- Maven
- PostgreSQL 8.0+
- Git

### Clonar el repositorio
- git clone https://github.com/BeLawliet/DevWave-SpringBoot-Arithmetic-Calculator.git

### Configurar la base de datos
- Crear una base de datos PostgreSQL llamada `db_operation_arithmetic`

Se debe de configurar en el archivo application.properties las propiedades correspondientes para PostgreSQL:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/db_operation_arithmetic
```

## Paquetes del Proyecto
El proyecto está organizado en los siguientes paquetes:

- `arithmetic.calculator.api.advice`: Gestiona de forma centralizada los errores más comunes que pueden ocurrir durante el uso de la API. Esta capa captura y devuelve respuestas claras y estructuradas ante excepciones como: Errores de validación, Errores aritméticos y errores de puntero nulo.
- `arithmetic.calculator.api.config`: Configura la seguridad de una aplicación Spring Boot con JWT (JSON Web Token). Sus características principales son: Implementa autenticación stateless con tokens JWT, Define puntos de acceso público como Endpoints de registro y login (/api/auth/login y /api/auth/register), Protege todos los demás endpoints con autenticación, Configura el validador de tokens JWT, Utiliza BCrypt para el cifrado de contraseñas.
- `com.example.calculator.persistence`: Contiene entidades y repositorios.
- `com.example.calculator.presentation`: Contiene controladores que actúan como punto de entrada a la aplicación y Dto para transferir información.
- `com.example.calculator.service`: Contiene la lógica de negocio de la aplicación estructurada en dos componentes principales. Interface, Implementaciones.
- `com.example.calculator.utils`: Contiene herramientas que encapsula toda la lógica relacionada con la gestión de tokens JWT, incluyendo generación, validación y extracción de información de tokens, ademas de *Anotations* personalizadas y *Specifications* de JPA.

## Controlladores

- La aplicación implementa una estructura modular de rutas `AuthController`, `OperationController` delegan la lógica de negocio a las capas inferiores. Cada controlador se especializa en un recurso o funcionalidad específica de la aplicación.

### Rutas:

**URL:**
```properties
http://localhost:8080
```
**OperationController:**

- **GET**
- `/api/history?page={page}&size={size}`: Lista paginada de operaciones del usuario.
- `/api/history?operation={operation}`: Buscar por tipo de operación.
- `/api/history?startDate={startDate}&endDate={endDate}`: Buscar por rango de fechas.
- `/api/history?field={field}&direction{direction}`: Ordenar lista por campo y direccion.
- `/api/history/{id}`: Detalle de una operación específica por su ID.


- **POST**
- `/api/calculate`: Agrega una nueva operación.


- **DELETE**
- `/api/delete/history/{id}`: Eliminar registro de operación.

**AuthController:**

- **POST**
- `/api/auth/register`: Registro de usuarios.
- `/api/auth/login`: Autenticación y generación de token.

## Recursos Adicionales

- **Colección de Postman**: Puedes utilizar la colección para probar fácilmente todos los endpoints definidos en la API.  
  [Descargar colección de Postman](src/main/resources/static/ArithmeticCalculator-RestAPI.postman_collection.json)

## Resumen
Este documento proporciona una visión general de la estructura del proyecto, sus componentes claves y configuraciones.
