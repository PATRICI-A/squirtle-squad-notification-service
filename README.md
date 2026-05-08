<div align="center">

# 🔔 PATRICI.A — Microservicio de Notificaciones

### *"Siempre informado, siempre conectado"*

---

### 🛠️ Stack Tecnológico

![Java](https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Mensajería-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)

### ☁️ Infraestructura & Calidad

![MongoDB](https://img.shields.io/badge/MongoDB-Atlas-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Container-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

### 🏗️ Arquitectura

![Hexagonal](https://img.shields.io/badge/Architecture-Hexagonal-blueviolet?style=for-the-badge)
![Clean Architecture](https://img.shields.io/badge/Clean-Architecture-blue?style=for-the-badge)
![WebSocket](https://img.shields.io/badge/WebSocket-STOMP-009688?style=for-the-badge)
![REST API](https://img.shields.io/badge/REST-API-009688?style=for-the-badge)

</div>

---

## 📑 Tabla de Contenidos

1. [👤 Integrantes](#1--integrantes)
2. [🎯 Objetivo del Microservicio](#2--objetivo-del-microservicio)
3. [⚡ Funcionalidades Principales](#3--funcionalidades-principales)
4. [📋 Estrategia de Versionamiento y Branches](#4--estrategia-de-versionamiento-y-branches)
   - [4.1 Convenciones para crear ramas](#41-convenciones-para-crear-ramas)
   - [4.2 Convenciones para crear commits](#42-convenciones-para-crear-commits)
5. [⚙️ Tecnologías Utilizadas](#5--tecnologías-utilizadas)
6. [🧩 Funcionalidades](#6--funcionalidades)
7. [📊 Diagramas](#7--diagramas)
8. [⚠️ Manejo de Errores](#8--manejo-de-errores)
9. [🧪 Evidencia de Pruebas y Ejecución](#9--evidencia-de-pruebas-y-cómo-ejecutarlas)
10. [🗂️ Organización del Código](#10--organización-del-código)
11. [🚀 Ejecución del Proyecto](#11--ejecución-del-proyecto)
12. [🤝 Contribuciones](#12--contribuciones)

---

## 1. 👤 Integrantes

- Cristian Guerrero
- Santiago Cajamarca
- Nicolas Sanchez
- Daniel Rodriguez

---

## 2. 🎯 Objetivo del Microservicio

El microservicio de Notificaciones es el componente del sistema **PATRICI.A** encargado de gestionar el sistema centralizado de alertas y recordatorios de la plataforma. Su propósito es garantizar que los usuarios estén siempre informados sobre la actividad relevante, independientemente de si tienen la aplicación abierta o no.

El servicio opera bajo un **modelo orientado a eventos**, consumiendo eventos publicados por otros módulos del sistema mediante colas de mensajería **RabbitMQ con topología de Topic Exchange**, lo que lo desacopla completamente de los módulos productores. Cuando el usuario está conectado, las notificaciones se entregan en tiempo real vía **WebSocket (STOMP)**; cuando no lo está, quedan persistidas en **MongoDB** para su consulta posterior (modelo in-app).

---

## 3. ⚡ Funcionalidades Principales

<div align="center">

| 💡 Funcionalidad | Descripción |
|---|---|
| **Entrega en Tiempo Real** | Entrega notificaciones instantáneas al usuario conectado mediante WebSocket STOMP sobre el topic `/topic/notifications/{userId}`. |
| **Persistencia In-App** | Persiste todas las notificaciones en MongoDB para que el usuario pueda consultarlas aunque no estuviera conectado en el momento de la generación. |
| **Gestión de Preferencias** | Permite a cada usuario habilitar o deshabilitar tipos de notificación de forma individual según sus preferencias. |
| **Recordatorios Automáticos** | Un scheduler evalúa eventos guardados cada 10 minutos y envía recordatorios automáticos a las 24h y a la 1h antes del inicio del evento. |
| **Consumo de Eventos Asíncrono** | Consume eventos de otros módulos (parches, social) vía RabbitMQ sin acoplamiento sincrónico con los productores. |

</div>

---

## 4. 📋 Estrategia de Versionamiento y Branches

### Estrategia de Ramas (Git Flow)

Manejamos **GitFlow** como modelo de ramificación para el control de versiones.

#### `main`
- **Propósito:** Rama estable con la versión final lista para demo/producción.
- **Reglas:** Solo recibe merges desde `release/*` y `hotfix/*`. Rama protegida: PR obligatorio con aprobaciones y CI en verde.

#### `develop`
- **Propósito:** Integración continua de trabajo; base de nuevas funcionalidades.
- **Reglas:** Recibe merges desde `feature/*`. Rama protegida similar a `main`.

#### `feature/*`
- **Propósito:** Desarrollo de una funcionalidad o configuración específica.
- **Base:** `develop`. **Cierre:** PR hacia `develop`.


---

### 4.1 Convenciones para crear ramas

**Formato:**
```
feature/[NombreFuncionalidad]
```

**Ejemplos:**
```
feature/NotificationService
feature/Inicializacion
feature/setup-devops
```

---

### 4.2 Convenciones para crear commits

**Formato:**
```
Feat: [Descripción de la acción realizada]
```
### Ejemplos:
- Feat: Configuracion Email
- Feat: Correcion Consumer Auth
- Configuracion Rabbit

---

## 5. ⚙️ Tecnologías Utilizadas

| **Tecnología / Herramienta** | **Uso principal en el proyecto** |
|---|---|
| **Java 21** | Lenguaje principal de desarrollo |
| **Spring Boot 3.3.0** | Framework principal del backend — gestión de dependencias y ciclo de vida de la aplicación |
| **Spring Security** | Autenticación y autorización mediante JWT |
| **Spring WebSocket + STOMP** | Canal de entrega de notificaciones en tiempo real al usuario conectado |
| **Spring AMQP / RabbitMQ** | Broker de mensajería asíncrona para consumo de eventos de otros módulos |
| **Spring Data MongoDB** | Persistencia de notificaciones, preferencias y recordatorios |
| **MongoDB Atlas** | Base de datos NoSQL en la nube — colección `notifications`, `notificationPreferences`, `eventReminders` |
| **Spring Mail (JavaMailSender)** | Envío de notificaciones transaccionales por correo electrónico |
| **OpenFeign** | Comunicación sincrónica con módulos externos cuando se requiere |
| **Lombok** | Reducción de código repetitivo con `@Builder`, `@Getter`, `@RequiredArgsConstructor` |
| **SpringDoc OpenAPI 2.5.0** | Generación automática de documentación Swagger UI |
| **JUnit 5 + Mockito** | Pruebas unitarias e integración con mocking de puertos y dependencias |
| **JaCoCo** | Reportes de cobertura de código |
| **Apache Maven 3.9** | Herramienta de construcción y gestión de dependencias |
| **Docker + Docker Compose** | Contenedorización del servicio y orquestación local con RabbitMQ y MongoDB |
| **GitHub Actions** | Pipeline de integración continua (build y pruebas automáticas) |

---

## 6. 🧩 Funcionalidades

---

### 1️⃣ Enviar Notificación

Crea y entrega una notificación a un usuario. Si el usuario está conectado vía WebSocket la recibe en tiempo real; si no, queda persistida en MongoDB.

**Endpoint:** `POST /api/notifications`

---

#### 📦 Estructura de la Solicitud (Request)

| Campo | Tipo | Restricciones | Descripción |
|---|---|---|---|
| userId | String | Obligatorio | Identificador del usuario destino |
| type | Enum | Obligatorio | Tipo de notificación (ver tipos disponibles) |
| title | String | Obligatorio | Título visible de la notificación |
| body | String | Obligatorio | Cuerpo del mensaje |
| referenceId | String | Opcional | ID del recurso que originó la notificación |

#### 📦 Estructura de la Respuesta (Response)

| Campo | Tipo | Descripción |
|---|---|---|
| id | String | Identificador único de la notificación |
| userId | String | Usuario al que pertenece |
| type | Enum | Tipo de notificación |
| channel | Enum | Canal de entrega: `IN_APP` o `EMAIL` |
| title | String | Título de la notificación |
| body | String | Cuerpo del mensaje |
| read | Boolean | Indica si fue leída |
| referenceId | String | ID del recurso relacionado |
| createdAt | DateTime | Fecha y hora de creación |

#### ✅ Happy Path

1. El sistema valida que `userId`, `type` y `body` no estén vacíos.
2. Se consultan las preferencias del usuario. Si el tipo está deshabilitado, se rechaza.
3. Se determina el canal (`IN_APP` si el userId no contiene `@`, `EMAIL` si sí).
4. La notificación se persiste en MongoDB.
5. Se entrega via el adaptador correspondiente al canal.
6. Se retorna `201 CREATED` con la notificación creada.

**Request:**
```json
POST /api/notifications
{
  "userId": "user-123",
  "type": "PARCHE_INVITATION",
  "title": "Te invitaron a un parche",
  "body": "Santiago te invitó a: Parche de cálculo",
  "referenceId": "parche-456"
}
```

**Response:**
```json
{
  "id": "6650a1f3e4b0c12d3a4f5678",
  "userId": "user-123",
  "type": "PARCHE_INVITATION",
  "channel": "IN_APP",
  "title": "Te invitaron a un parche",
  "body": "Santiago te invitó a: Parche de cálculo",
  "read": false,
  "referenceId": "parche-456",
  "createdAt": "2026-04-15T10:30:00"
}
```

#### 📊 Errores manejados

| Código HTTP | Escenario | Mensaje |
|---|---|---|
| 400 | Campos obligatorios vacíos | `"el campo userId es obligatorio"` |
| 409 | Tipo deshabilitado en preferencias | `"Notification type NEARBY_PARCHE is disabled for user user-123"` |
| 500 | Error de persistencia | Error genérico del servidor |

---

### 2️⃣ Listar Notificaciones del Usuario

Retorna las notificaciones del usuario autenticado de forma paginada, ordenadas por fecha descendente.

**Endpoint:** `GET /api/notifications`

**Headers:** `X-User-Id: {userId}`

**Parámetros:**

| Parámetro | Tipo | Default | Descripción |
|---|---|---|---|
| page | int | 0 | Número de página (base 0) |
| size | int | 20 | Tamaño de página |

**Response:** `List<NotificationResponse>` (misma estructura que el response de enviar notificación)

#### 📊 Errores manejados

| Código HTTP | Escenario |
|---|---|
| 400 | Header `X-User-Id` ausente o parámetros inválidos |

---

### 3️⃣ Contar Notificaciones No Leídas

Retorna el número de notificaciones no leídas del usuario.

**Endpoint:** `GET /api/notifications/unread/count`

**Headers:** `X-User-Id: {userId}`

**Response:**
```json
{
  "count": 5
}
```

---

### 4️⃣ Marcar Notificación como Leída

Marca una notificación específica del usuario como leída.

**Endpoint:** `PUT /api/notifications/{notificationId}/read`

**Headers:** `X-User-Id: {userId}`

**Response:** `204 No Content`

#### 📊 Errores manejados

| Código HTTP | Escenario |
|---|---|
| 404 | Notificación no encontrada o no pertenece al usuario |

---

### 5️⃣ Marcar Todas como Leídas

Marca todas las notificaciones del usuario como leídas en una sola operación.

**Endpoint:** `PUT /api/notifications/read`

**Headers:** `X-User-Id: {userId}`

**Response:** `204 No Content`

---

### 6️⃣ Consultar Preferencias de Notificación

Retorna las preferencias de notificación del usuario. Si no existen registros previos, devuelve los valores por defecto.

**Endpoint:** `GET /api/notifications/preferences`

**Headers:** `X-User-Id: {userId}`

**Response:**
```json
{
  "connectionRequest": true,
  "parcheMessage": true,
  "eventReminder": true,
  "nearbyParche": false,
  "achievementUnlocked": true,
  "parcheInvitation": true
}
```

---

### 7️⃣ Actualizar Preferencia de Notificación

Habilita o deshabilita un tipo de notificación específico para el usuario.

**Endpoint:** `PUT /api/notifications/preferences`

**Headers:** `X-User-Id: {userId}`

**Request:**
```json
{
  "type": "NEARBY_PARCHE",
  "enabled": true
}
```

**Response:** `200 OK` con el objeto `NotificationPreferencesResponse` actualizado.

#### 📊 Errores manejados

| Código HTTP | Escenario |
|---|---|
| 400 | Tipo de notificación inválido o campo `enabled` ausente |

---

### 8️⃣ Crear Recordatorio de Evento

Registra un recordatorio para un evento guardado. El scheduler enviará notificaciones automáticas **24 horas** y **1 hora** antes del inicio del evento.

**Endpoint:** `POST /api/event-reminders`

**Request:**
```json
{
  "userId": "user-123",
  "eventId": "event-789",
  "eventDate": "2026-05-10T14:00:00"
}
```

**Response:** `201 CREATED`
```json
{
  "id": "reminder-001",
  "userId": "user-123",
  "eventId": "event-789",
  "eventDate": "2026-05-10T14:00:00",
  "reminded24h": false,
  "reminded1h": false
}
```

#### 📊 Errores manejados

| Código HTTP | Escenario |
|---|---|
| 400 | Fecha en el pasado o campos obligatorios ausentes |

---

### 🔌 WebSocket — Notificaciones en Tiempo Real

La conexión WebSocket se realiza mediante **STOMP sobre SockJS**.

| Campo | Valor |
|---|---|
| **Endpoint de conexión** | `/ws/notifications` |
| **Topic de suscripción** | `/topic/notifications/{userId}` |
| **Prefijo de aplicación** | `/app` |

**Ejemplo de suscripción (JavaScript):**
```javascript
const socket = new SockJS('/ws/notifications');
const stompClient = Stomp.over(socket);

stompClient.connect({}, () => {
  stompClient.subscribe(`/topic/notifications/${userId}`, (message) => {
    const notification = JSON.parse(message.body);
    console.log('Nueva notificación:', notification);
  });
});
```

---

### 📨 Consumo de Eventos vía RabbitMQ

El servicio actúa como **consumidor** en la arquitectura de mensajería del sistema. Los otros módulos publican eventos y este servicio los transforma en notificaciones.

| Consumer | Exchange | Cola | Routing Key | Evento |
|---|---|---|---|---|
| `ParcheNotificationConsumer` | `parche.exchange` | `parche.invitation.queue` | `parche.invitation` | Invitación a parche |
| `ParcheNotificationConsumer` | `parche.exchange` | `parche.nearby.queue` | `parche.nearby` | Parche cercano |
| `SocialNotificationConsumer` | `social.exchange` | `social.connection.queue` | `social.connection` | Solicitud de conexión |

---

## 7. 📊 Diagramas

### 🏗️ Diagrama de Componentes


![PDCE.drawio.png](PDCE.drawio.png)

El microservicio implementa **Arquitectura Hexagonal (Ports & Adapters)** con el siguiente flujo:

- **Entrypoints:** `NotificationController`, `EventReminderController`, `NotificationWebSocketController` (REST) y `ParcheNotificationConsumer`, `SocialNotificationConsumer` (RabbitMQ).
- **Puertos de entrada (ports/in):** Interfaces que definen los casos de uso del dominio.
- **Casos de uso (application):** Implementaciones que orquestan la lógica, verifican preferencias y despachan por canal.
- **Puertos de salida (ports/out):** `NotificationRepository`, `PreferencesRepository`, `EventReminderRepository`, `NotificationDeliveryPort`.
- **Adaptadores:** Implementaciones MongoDB y WebSocket/Email de los puertos de salida.

### 🗄️ Modelo de Datos (MongoDB)

El servicio utiliza tres colecciones independientes:

**`notifications`**

| Campo | Tipo | Descripción |
|---|---|---|
| _id | ObjectId | ID generado por MongoDB |
| userId | String | Usuario destino |
| type | Enum | Tipo de notificación |
| channel | Enum | `IN_APP` / `EMAIL` |
| title | String | Título visible |
| body | String | Cuerpo del mensaje |
| read | Boolean | Estado de lectura |
| referenceId | String | Recurso relacionado (opcional) |
| createdAt | DateTime | Fecha de creación |

**`notificationPreferences`**

| Campo | Tipo | Descripción |
|---|---|---|
| userId | String | Usuario propietario |
| connectionRequest | Boolean | Recibir alertas de solicitudes de conexión |
| parcheMessage | Boolean | Recibir alertas de mensajes en parches |
| eventReminder | Boolean | Recibir recordatorios de eventos |
| nearbyParche | Boolean | Recibir alertas de parches cercanos |
| achievementUnlocked | Boolean | Recibir alertas de logros |
| parcheInvitation | Boolean | Recibir invitaciones a parches |
| updatedAt | DateTime | Última actualización |

**`eventReminders`**

| Campo | Tipo | Descripción |
|---|---|---|
| userId | String | Usuario que guardó el evento |
| eventId | String | Identificador del evento |
| eventDate | DateTime | Fecha del evento |
| reminded24h | Boolean | Si ya se envió el recordatorio de 24h |
| reminded1h | Boolean | Si ya se envió el recordatorio de 1h |

---

## 8. ⚠️ Manejo de Errores

El servicio implementa un **handler centralizado** con `@RestControllerAdvice` en la capa `entrypoints/advice`.

### Excepciones del dominio

| Código HTTP | Tipo | Escenario |
|---|---|---|
| 400 | `InvalidNotificationException` | Campos obligatorios ausentes (`userId`, `type`, `body`) |
| 400 | `MethodArgumentNotValidException` | Validaciones de DTOs fallidas |
| 404 | `NotificationNotFoundException` | Notificación no encontrada o no pertenece al usuario |
| 409 | `NotificationTypeDisabledException` | El tipo de notificación está deshabilitado en las preferencias del usuario |
| 500 | `Exception` (genérico) | Error inesperado del servidor |

### Estructura del response de error

```json
{
   "status": 404,
   "message": "Notification not found for id: 6650a1f3e4b0c12d3a4f5678",
   "timestamp": "2026-04-15T10:30:00"
}
```

---

## 9. 🧪 Evidencia de Pruebas y Cómo Ejecutarlas

### 🎯 Tipos de pruebas implementadas

| Tipo | Descripción | Herramientas |
|---|---|---|
| **Pruebas Unitarias** | Validan el funcionamiento aislado de cada caso de uso mockeando puertos y dependencias | JUnit 5 + Mockito |
| **Cobertura de Código** | Mide el porcentaje de código cubierto por las pruebas | JaCoCo |

### Clases de prueba

| Clase | Descripción |
|---|---|
| `SendNotificationUseCaseImplTest` | Valida envío exitoso, campos vacíos y tipo deshabilitado |
| `GetNotificationsUseCaseImplTest` | Valida paginación y consulta por usuario |
| `GetUnreadCountUseCaseImplTest` | Valida conteo de no leídas |
| `MarkAsReadUseCaseImplTest` | Valida marcar una y todas como leídas |
| `NotificationMapperTest` | Valida transformaciones entre dominio y DTOs |

### 🚀 Cómo ejecutar las pruebas

**Ejecutar todas las pruebas:**
```bash
mvn clean test
```

**Generar reporte de cobertura JaCoCo:**
```bash
mvn clean test jacoco:report
```
El reporte HTML estará en: `target/site/jacoco/index.html`

**Ejecutar una prueba específica:**
```bash
mvn test -Dtest=SendNotificationUseCaseImplTest
```

### ✅ Criterios de aceptación

- ✅ Cobertura mínima del **80%** en servicios y casos de uso
- ✅ Todas las pruebas en estado **PASSED**
- ✅ Pruebas de casos felices **y** casos de error implementados

---

## 10. 🗂️ Organización del Código

El microservicio sigue **Arquitectura Hexagonal (Ports & Adapters)** con Clean Architecture.

```
squirtle-squad-notification-service/
│
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/patricia/notification/
│   │   │   │
│   │   │   ├── 📁 domain/                          # 🟢 DOMINIO (sin dependencias externas)
│   │   │   │   ├── 📁 model/                       # Entidades: Notification, NotificationPreferences, EventReminder
│   │   │   │   │   └── 📁 enums/                   # NotificationType, NotificationChannel
│   │   │   │   ├── 📁 ports/
│   │   │   │   │   ├── 📁 in/                      # Interfaces de casos de uso (puertos de entrada)
│   │   │   │   │   └── 📁 out/                     # Interfaces de repositorios y entrega (puertos de salida)
│   │   │   │   └── 📁 exceptions/                  # Excepciones de dominio
│   │   │   │
│   │   │   ├── 📁 application/                     # 🔵 APLICACIÓN
│   │   │   │   ├── 📁 usecase/                     # Implementaciones de los casos de uso
│   │   │   │   ├── 📁 service/                     # EventReminderService (scheduler)
│   │   │   │   ├── 📁 dto/
│   │   │   │   │   ├── 📁 request/                 # DTOs de entrada
│   │   │   │   │   └── 📁 response/                # DTOs de salida
│   │   │   │   └── 📁 mapper/                      # NotificationMapper
│   │   │   │
│   │   │   ├── 📁 entrypoints/                     # 🟠 PUERTOS DE ENTRADA
│   │   │   │   ├── 📁 rest/controller/             # NotificationController, EventReminderController
│   │   │   │   ├── 📁 messaging/
│   │   │   │   │   ├── 📁 consumer/                # ParcheNotificationConsumer, SocialNotificationConsumer
│   │   │   │   │   └── 📁 dto/                     # DTOs de eventos RabbitMQ
│   │   │   │   └── 📁 advice/                      # NotificationExceptionHandler
│   │   │   │
│   │   │   └── 📁 infrastructure/                  # 🔴 INFRAESTRUCTURA
│   │   │       ├── 📁 adapters/
│   │   │       │   ├── 📁 adapter/                 # WebSocketNotificationAdapter, EmailNotificationAdapter
│   │   │       │   └── 📁 persistence/             # Repositorios Mongo, documentos y mappers de persistencia
│   │   │       └── 📁 config/                      # RabbitMQConfig, WebSocketConfig, MongoConfig, SchedulerConfig
│   │   │
│   │   └── 📁 resources/
│   │       └── application.properties
│   │
│   └── 📁 test/                                    # 🧪 PRUEBAS UNITARIAS
│
├── 📄 Dockerfile
├── 📄 docker-compose.yml
├── 📄 pom.xml
└── 📄 README.md
```

### 🏛️ Principios aplicados

| Principio | Implementación |
|---|---|
| **Separación de responsabilidades** | Cada capa tiene un propósito único y bien definido |
| **Inversión de dependencias** | Las capas externas dependen de interfaces definidas en el dominio |
| **Independencia del framework** | La lógica de negocio no depende de Spring, MongoDB ni RabbitMQ |
| **Desacoplamiento por eventos** | Los módulos productores no conocen al notification-service |
| **Testabilidad** | Fácil crear pruebas unitarias mockeando puertos y adaptadores |

---

## 11. 🚀 Ejecución del Proyecto

### 📋 Prerrequisitos

- **Java 21**
- **Maven 3.9+**
- **Docker** (recomendado para levantar RabbitMQ y MongoDB localmente)

### 🛠️ Opción 1: Ejecución Local (Maven)

```bash
# 1. Clonar repositorio
git clone https://github.com/tu-org/squirtle-squad-notification-service.git

# 2. Levantar dependencias (RabbitMQ + MongoDB) con Docker Compose
docker-compose up -d rabbitmq mongodb

# 3. Ejecutar la aplicación
mvn spring-boot:run
```

📍 **URL Local:** `http://localhost:8082`
📚 **Swagger UI:** `http://localhost:8082/swagger-ui.html`
📡 **WebSocket:** `ws://localhost:8082/ws/notifications`

### 🐳 Opción 2: Ejecución completa con Docker

```bash
docker-compose up --build -d
```

### ⚙️ Variables de entorno principales

| Variable | Descripción | Default |
|---|---|---|
| `SPRING_DATA_MONGODB_URI` | URI de conexión a MongoDB Atlas | Configurado en `application.properties` |
| `SPRING_RABBITMQ_HOST` | Host del broker RabbitMQ | `localhost` |
| `SPRING_RABBITMQ_PORT` | Puerto RabbitMQ | `5672` |
| `SERVER_PORT` | Puerto del servicio | `8082` |

---

## 12. 🤝 Contribuciones

El equipo **Squirtle Squad** aplicó la metodología **Scrum** con sprints semanales.

### 👥 Roles del equipo

| Rol | Responsabilidad |
|---|---|
| **Product Owner** | Priorización del backlog y definición de criterios de aceptación |
| **Scrum Master** | Facilitación de ceremonias y eliminación de impedimentos |
| **Developers** | Diseño, implementación y pruebas de los microservicios |

### 🔄 Herramientas de gestión

- **Jira** — Seguimiento de tareas por sprints
- **GitHub Projects** — Tablero de trabajo del equipo
- **Draw.io** — Diagramas de arquitectura

---

<div align="center">

### 🐢 Equipo **Squirtle Squad**

![Team](https://img.shields.io/badge/Team-Squirtle_Squad-6DB33F?style=for-the-badge&logo=github&logoColor=white)
![Course](https://img.shields.io/badge/Course-DOSW-orange?style=for-the-badge)
![Year](https://img.shields.io/badge/Year-2026--1-blue?style=for-the-badge)

> 💡 **PATRICI.A** es un proyecto académico de la Escuela Colombiana de Ingeniería Julio Garavito, construido con arquitectura de microservicios orientada a producción.

**🎓 Escuela Colombiana de Ingeniería Julio Garavito**

</div>