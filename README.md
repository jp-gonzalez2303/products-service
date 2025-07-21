# Products Service

Microservicio REST para la gestión de productos desarrollado con Spring Boot 3.2.6.

## 🚀 Tecnologías

- **Java 17**
- **Spring Boot 3.2.6**
- **Spring Data JPA**
- **PostgreSQL**
- **Gradle**
- **MapStruct**
- **OpenAPI 3**
- **JaCoCo** (Cobertura de código)

## 📋 Prerrequisitos

- Java 17+
- Gradle 8.4+
- PostgreSQL

## ⚙️ Configuración

### Variables de Entorno

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/microdb
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
PRODUCTS_API_KEY=your-api-key
```

### Base de Datos

El servicio utiliza PostgreSQL con configuración automática de esquemas (`hibernate.ddl-auto=update`).

## 🏃‍♂️ Ejecución

### Desarrollo Local

```bash
./gradlew bootRun
```

### Construcción

```bash
./gradlew build
```

### Docker

```bash
docker build -t products-service .
docker run -p 8080:8080 products-service
```

## 📡 API Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/v1/products` | Crear producto |
| `GET` | `/api/v1/products/{id}` | Obtener producto por ID |
| `GET` | `/api/v1/products` | Listar todos los productos |

### Modelo de Producto

```json
{
  "id": 1,
  "nombre": "Producto Ejemplo",
  "precio": 99.99,
  "descripcion": "Descripción del producto"
}
```

## 🔐 Autenticación

El servicio utiliza autenticación por API Key. Incluye el header `x-api-key` en todas las peticiones a endpoints `/api/*`.

## 📊 Diagramas de Secuencia

### Crear Producto

```mermaid
sequenceDiagram
    participant Client as Cliente
    participant Filter as ApiKeyFilter
    participant Controller as ProductController
    participant Service as ProductService
    participant Mapper as ProductMapper
    participant Repository as ProductRepository
    participant DB as PostgreSQL

    Client->>Filter: POST /api/v1/products<br/>x-api-key: {apiKey}
    Filter->>Filter: Validar API Key
    Filter->>Controller: Request autorizado
    Controller->>Controller: Validar @Valid Product
    Controller->>Service: createProduct(product)
    Service->>Repository: findByNameIgnoreCase(nombre)
    Repository->>DB: SELECT * FROM products WHERE name ILIKE ?
    DB-->>Repository: Resultado
    alt Producto no existe
        Repository-->>Service: Optional.empty()
        Service->>Mapper: toEntity(product)
        Mapper-->>Service: ProductEntity
        Service->>Repository: save(productEntity)
        Repository->>DB: INSERT INTO products
        DB-->>Repository: ProductEntity guardado
        Repository-->>Service: ProductEntity
        Service->>Mapper: toDTO(productEntity)
        Mapper-->>Service: Product DTO
        Service-->>Controller: Product
        Controller-->>Client: 201 Created + Product
    else Producto ya existe
        Repository-->>Service: Optional<ProductEntity>
        Service-->>Controller: ProductAlreadyExistsException
        Controller-->>Client: 409 Conflict + Error
    end
```

### Obtener Producto por ID

```mermaid
sequenceDiagram
    participant Client as Cliente
    participant Filter as ApiKeyFilter
    participant Controller as ProductController
    participant Service as ProductService
    participant Mapper as ProductMapper
    participant Repository as ProductRepository
    participant DB as PostgreSQL

    Client->>Filter: GET /api/v1/products/{id}<br/>x-api-key: {apiKey}
    Filter->>Filter: Validar API Key
    Filter->>Controller: Request autorizado
    Controller->>Service: findById(id)
    Service->>Repository: findById(id)
    Repository->>DB: SELECT * FROM products WHERE id = ?
    alt Producto encontrado
        DB-->>Repository: ProductEntity
        Repository-->>Service: Optional<ProductEntity>
        Service->>Mapper: toDTO(productEntity)
        Mapper-->>Service: Product DTO
        Service-->>Controller: Product
        Controller-->>Client: 200 OK + Product
    else Producto no encontrado
        DB-->>Repository: null
        Repository-->>Service: Optional.empty()
        Service-->>Controller: ProductNotFoundException
        Controller-->>Client: 404 Not Found + Error
    end
```

### Listar Todos los Productos

```mermaid
sequenceDiagram
    participant Client as Cliente
    participant Filter as ApiKeyFilter
    participant Controller as ProductController
    participant Service as ProductService
    participant Mapper as ProductMapper
    participant Repository as ProductRepository
    participant DB as PostgreSQL

    Client->>Filter: GET /api/v1/products<br/>x-api-key: {apiKey}
    Filter->>Filter: Validar API Key
    Filter->>Controller: Request autorizado
    Controller->>Service: findAll()
    Service->>Repository: findAll()
    Repository->>DB: SELECT * FROM products
    DB-->>Repository: List<ProductEntity>
    Repository-->>Service: List<ProductEntity>
    Service->>Mapper: toDTO() para cada entidad
    Mapper-->>Service: List<Product> DTOs
    Service-->>Controller: List<Product>
    Controller-->>Client: 200 OK + List<Product>
```

## 🔧 Características

- **Validación de datos** con Bean Validation
- **Mapeo de entidades** con MapStruct
- **Documentación API** con OpenAPI 3
- **Monitoreo** con Spring Actuator
- **Seguridad** con API Key Filter
- **Cobertura de pruebas** mínima del 80%

## 🧪 Testing

```bash
./gradlew test
./gradlew jacocoTestReport
```

## 📊 Monitoreo

- **Health Check**: `/actuator/health`
- **Info**: `/actuator/info`
- **API Docs**: `/swagger-ui.html`

## 📁 Estructura del Proyecto

```
src/main/java/co/com/linktic/products/
├── config/          # Configuraciones
├── controller/      # Controladores REST
├── entity/          # Entidades JPA
├── exception/       # Manejo de excepciones
├── mapper/          # Mappers MapStruct
├── model/           # DTOs
├── repository/      # Repositorios
└── service/         # Lógica de negocio
```