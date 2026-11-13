# API REST con Spring Boot y JWT

## Descripción del proyecto

Este proyecto consiste en el desarrollo de una API REST utilizando Spring Boot, que permite administrar usuarios, categorías y productos mediante operaciones CRUD.

La API implementa autenticación y autorización mediante JSON Web Tokens (JWT), utilizando Spring Security para proteger los endpoints. También utiliza DTO para controlar la información que se recibe y devuelve, evitando exponer directamente las entidades de la base de datos.

El proyecto está desarrollado como parte de la Actividad 4 de Spring Boot.

## Tecnologías utilizadas

- Java 21
- Spring Boot 4.1.2
- Spring Security
- Spring Data JPA
- JWT (JSON Web Token)
- MySQL / MariaDB
- Maven
- Bruno
- Git y GitHub

## Características principales

- Registro de usuarios.
- Inicio de sesión mediante correo electrónico y contraseña.
- Generación y validación de tokens JWT.
- Protección de endpoints mediante Spring Security.
- Contraseñas cifradas con BCrypt.
- Administración de categorías.
- Administración de productos.
- Relación entre categorías y productos.
- Validación de datos mediante Bean Validation.
- Uso de DTO para las solicitudes y respuestas.
- Manejo de códigos de estado HTTP.
- Listado de productos con paginación.

## Estructura del proyecto

El proyecto está organizado en los siguientes paquetes:

```text
src/main/java/com/karlmt/spring/app4/
│
├── config/
│   └── Configuraciones de la aplicación
│
├── controller/
│   ├── AuthController.java
│   ├── CategoriaController.java
│   └── ProductoController.java
│
├── dto/
│   ├── RegisterRequest.java
│   ├── LoginRequest.java
│   ├── AuthResponse.java
│   ├── CategoriaRequestDTO.java
│   ├── CategoriaResponseDTO.java
│   ├── ProductoRequestDTO.java
│   └── ProductoResponseDTO.java
│
├── models/
│   ├── Usuario.java
│   ├── Categoria.java
│   └── Producto.java
│
├── repository/
│   ├── UsuarioRepository.java
│   ├── CategoriaRepository.java
│   └── ProductoRepository.java
│
├── service/
│   ├── CategoriaService.java
│   └── ProductoService.java
│
├── security/
│   ├── JwtService.java
│   └── Configuración de seguridad y JWT
│
└── exception/
    └── GlobalExceptionHandler.java
```

## Base de datos

La aplicación utiliza una base de datos llamada:

`springboot_actividad4`

Se manejan las siguientes entidades:

| Entidad | Descripción |
|---|---|
| Usuario | Almacena los datos de los usuarios registrados. |
| Categoria | Almacena las categorías de los productos. |
| Producto | Almacena los productos y su información. |

La relación entre las entidades principales es de uno a muchos: una categoría puede tener varios productos y cada producto pertenece a una categoría.

## Autenticación con JWT

La API utiliza JWT para autenticar a los usuarios.

El proceso funciona de la siguiente manera:

1. El usuario se registra mediante el endpoint de registro.
2. El usuario inicia sesión proporcionando su correo electrónico y contraseña.
3. El servidor valida sus credenciales.
4. Si son correctas, genera un token JWT.
5. El usuario utiliza ese token para acceder a los endpoints protegidos.
6. Spring Security valida el token antes de permitir el acceso.

Las contraseñas se almacenan cifradas mediante BCrypt.

## Endpoints de autenticación

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/auth/register` | Registrar un usuario. |
| POST | `/api/auth/login` | Iniciar sesión y obtener un JWT. |

## Endpoints de categorías

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/categorias` | Consultar todas las categorías. |
| GET | `/api/categorias/{id}` | Consultar una categoría por ID. |
| POST | `/api/categorias` | Registrar una categoría. |
| PUT | `/api/categorias/{id}` | Actualizar una categoría. |
| DELETE | `/api/categorias/{id}` | Eliminar una categoría. |

## Endpoints de productos

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/productos` | Consultar productos con paginación. |
| GET | `/api/productos/{id}` | Consultar un producto por ID. |
| POST | `/api/productos` | Registrar un producto. |
| PUT | `/api/productos/{id}` | Actualizar un producto. |
| DELETE | `/api/productos/{id}` | Eliminar un producto. |

### Paginación

El listado de productos permite utilizar parámetros para controlar la paginación.

Ejemplo:

```text
/api/productos?page=0&size=10&sort=nombre
```

## Seguridad

Los endpoints de registro e inicio de sesión son públicos.

Los demás endpoints requieren un token JWT válido.

Para realizar una petición protegida se debe enviar el token en la cabecera:

```text
Authorization: Bearer TU_TOKEN_JWT
```

Si se intenta acceder a un endpoint protegido sin un token válido, la API devuelve:

```json
{
  "status": 401,
  "error": "Unauthorized"
}
```

## Códigos de respuesta HTTP

| Código | Descripción |
|---|---|
| 200 | Petición realizada correctamente. |
| 201 | Recurso creado correctamente. |
| 204 | Recurso eliminado correctamente, cuando corresponde. |
| 400 | Error de validación o solicitud incorrecta. |
| 401 | No autorizado: falta un token válido o las credenciales son incorrectas. |
| 404 | Recurso no encontrado. |
| 409 | Conflicto, por ejemplo, cuando se intenta registrar un correo ya existente. |

## Pruebas con Bruno

Para comprobar el funcionamiento de la API se utiliza Bruno.

La colección de pruebas incluye solicitudes para:

- Registrar usuarios.
- Iniciar sesión y obtener el token JWT.
- Consultar categorías.
- Registrar categorías.
- Actualizar categorías.
- Eliminar categorías.
- Consultar productos.
- Registrar productos.
- Actualizar productos.
- Eliminar productos.
- Comprobar el acceso a endpoints protegidos.

Para probar los endpoints protegidos se debe configurar la autenticación de tipo Bearer Token y proporcionar el JWT obtenido al iniciar sesión.

La colección de Bruno se encuentra incluida en el repositorio del proyecto.

## Ejecución local

### 1. Clonar el repositorio

```bash
git clone https://github.com/Karl335-lm/MBKact4_t4.git
```

### 2. Entrar al proyecto

```bash
cd MBKact4_t4
```

### 3. Configurar la base de datos

Crear una base de datos llamada:

```sql
CREATE DATABASE springboot_actividad4;
```

Configurar las credenciales de conexión mediante variables de entorno:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
APP_JWT_SECRET
APP_JWT_EXPIRATION
```

No se deben publicar contraseñas ni claves JWT reales en el repositorio.

### 4. Ejecutar el proyecto

Desde la terminal:

```bash
mvn spring-boot:run
```

La aplicación se ejecuta localmente en el puerto **8088**.

## Despliegue en VPS

La aplicación se encuentra desplegada en un servidor VPS utilizando Amazon EC2.

**Puerto de ejecución:** 8088

**Dirección base:** `18.218.44.223:8088`

Para probar los endpoints se puede utilizar Bruno enviando las solicitudes HTTP correspondientes.

## Autor

**Karla Martínez Bautista**

Proyecto desarrollado para la Actividad 4 de Spring Boot.

## Repositorio

GitHub: `Karl335-lm/MBKact4_t4`