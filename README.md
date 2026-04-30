# squirtle-squad-notification-service

## 🚀 Setup Inicial (Instrucciones para Stiven, Maria, Diego y Andres)

Para clonar y levantar el entorno local de desarrollo con un solo comando, sigue estos pasos:

1. **Clonar el repositorio y entrar a la carpeta del microservicio**:
   ```bash
   git clone <URL_DEL_REPO>
   cd squirtle-squad-notification-service
   ```

2. **Cambiar a la rama de desarrollo (develop)**:
   ```bash
   git checkout develop
   ```
   *(Nota: Si quieres probar esta feature específica antes de integrarla, usa `git checkout feature/setup-devops`)*

3. **Levantar los servicios con Docker Compose**:
   ```bash
   docker-compose up -d --build
   ```
   Este comando levantará:
   - El microservicio en el puerto `8080`.
   - PostgreSQL 15 en el puerto `5432`.
   - Redis en el puerto `6379`.
   - RabbitMQ en el puerto `5672` (y la consola web de administración en `15672`).

Para detener los servicios, simplemente ejecuta:
```bash
docker-compose down
```

---## Tabla de Contenido

1. [Descripción del Módulo](#1-descripción-del-módulo)
2. [Análisis de Tecnologías y Decisiones de Diseño Arquitectónico](#2-análisis-de-tecnologías-y-decisiones-de-diseño-arquitectónico)
    - 2.1 [Lenguaje de Programación](#21-lenguaje-de-programación)
    - 2.2 [Framework](#22-framework)
    - 2.3 [Base de Datos](#23-base-de-datos)
    - 2.4 [Servicio de Notificaciones Push](#24-servicio-de-notificaciones-push)
    - 2.5 [Broker de Mensajes](#25-broker-de-mensajes)
    - 2.6 [Herramientas de Desarrollo](#26-herramientas-de-desarrollo)
3. [Diagrama de Contexto del Módulo](#3-diagrama-de-contexto-del-módulo)
4. [Diagrama de Datos](#4-diagrama-de-datos)
5. [Diagrama de Clases](#5-diagrama-de-clases)
6. [Planteamiento de la Arquitectura](#6-planteamiento-de-la-arquitectura)
7. [Diagrama de Componentes](#7-diagrama-de-componentes)
8. [Diagrama de Despliegue](#8-diagrama-de-despliegue)
9. [Funcionalidades](#9-funcionalidades)
    - 9.1 [RF12 — Notificaciones y Recordatorios](#91-rf12--notificaciones-y-recordatorios)
10. [Manejo de Errores](#10-manejo-de-errores)
11. [Fuentes Bibliográficas](#11-fuentes-bibliográficas)
12. [Historial de Revisión](#12-historial-de-revisión)

---

## 1. Descripción del Módulo

El **Notification Service** es el microservicio de PATRICIA — ECI Social Campus responsable de mantener a los estudiantes informados sobre la actividad relevante que ocurre en la plataforma. Gestiona dos canales de notificación complementarios: las **notificaciones in-app** persistidas y consultables dentro de la aplicación, y las **notificaciones push** entregadas al dispositivo del estudiante mediante Firebase Cloud Messaging (FCM), incluso cuando la aplicación se encuentra cerrada.

El servicio opera bajo un modelo **orientado a eventos**: en lugar de recibir solicitudes directas de creación de notificaciones, se suscribe a eventos publicados por los demás microservicios de la plataforma. Cuando el **Matching Service** (Equipo 2) registra un nuevo match, cuando el **Parche Service** (Equipo 3) genera una invitación, o cuando el **Event Service** (Equipo 5) publica un evento próximo, este servicio consume esos eventos y transforma cada uno en la alerta correspondiente para el usuario destinatario.

Adicionalmente, expone endpoints REST que permiten al estudiante consultar su bandeja de notificaciones in-app, marcarlas como leídas de forma individual o en bloque, y configurar sus preferencias de notificación para activar o desactivar cada tipo de alerta de forma independiente.

Este microservicio forma parte del módulo de Comunicación (Equipo 3) y se despliega de forma independiente. Al ser consumidor de eventos y no productor de lógica de negocio crítica, su eventual fallo no afecta el funcionamiento de los módulos core de la plataforma.

**Requerimientos que cubre:**
- `RF12` — Notificaciones y Recordatorios: alertas push para parches, eventos guardados, nuevas solicitudes de conexión y recordatorios automáticos con 24 horas y 1 hora de anticipación.

---

## 2. Análisis de Tecnologías y Decisiones de Diseño Arquitectónico

### 2.1 Lenguaje de Programación

| Criterio | <!-- tecnología A --> | <!-- tecnología B --> |
|---|---|---|
| <!-- criterio --> | | |

> **Selección:** <!-- tecnología seleccionada -->
> **Razón:** <!-- justificación -->

---

### 2.2 Framework

| Criterio | <!-- tecnología A --> | <!-- tecnología B --> |
|---|---|---|
| <!-- criterio --> | | |

> **Selección:** <!-- tecnología seleccionada -->
> **Razón:** <!-- justificación -->

---

### 2.3 Base de Datos

| Criterio | <!-- tecnología A --> | <!-- tecnología B --> |
|---|---|---|
| <!-- criterio --> | | |

> **Selección:** <!-- tecnología seleccionada -->
> **Razón:** <!-- justificación -->

---

### 2.4 Servicio de Notificaciones Push

| Criterio | <!-- tecnología A --> | <!-- tecnología B --> |
|---|---|---|
| <!-- criterio --> | | |

> **Selección:** <!-- tecnología seleccionada -->
> **Razón:** <!-- justificación -->

---

### 2.5 Broker de Mensajes

| Criterio | <!-- tecnología A --> | <!-- tecnología B --> |
|---|---|---|
| <!-- criterio --> | | |

> **Selección:** <!-- tecnología seleccionada -->
> **Razón:** <!-- justificación -->

---

### 2.6 Herramientas de Desarrollo

| Herramienta | Uso |
|---|---|
| <!-- herramienta --> | <!-- uso --> |

---

## 3. Diagrama de Contexto del Módulo

> _Diagrama por agregar._

<!-- Descripción del diagrama -->

---

## 4. Diagrama de Datos

> _Diagrama por agregar._

### Documentos / Colecciones

| Documento | Descripción |
|---|---|
| <!-- documento --> | <!-- descripción --> |

---

## 5. Diagrama de Clases

> _Diagrama por agregar._

<!-- Descripción de la arquitectura y patrones utilizados -->

---

## 6. Planteamiento de la Arquitectura

<!-- Descripción de la arquitectura limpia aplicada al módulo -->

---

## 7. Diagrama de Componentes

> _Diagrama por agregar._

<!-- Descripción de la interacción con otros módulos -->

---

## 8. Diagrama de Despliegue

> _Diagrama por agregar._

<!-- Descripción del despliegue -->

---

## 9. Funcionalidades

### 9.1 RF12 — Notificaciones y Recordatorios

> _Diagrama de secuencia por agregar._

<!-- Descripción del flujo -->

#### Endpoints REST

##### `GET /api/notifications`

**Query Params:**

| Parámetro | Tipo | Descripción |
|---|---|---|
| page | Integer | Número de página |
| size | Integer | Tamaño de página |

**Response `200`:**

| Campo | Tipo | Descripción |
|---|---|---|
| <!-- campo --> | <!-- tipo --> | <!-- descripción --> |

---

##### `PATCH /api/notifications/{notificationId}/read`

**Response `200`:**

| Campo | Tipo | Descripción |
|---|---|---|
| <!-- campo --> | <!-- tipo --> | <!-- descripción --> |

---

##### `PATCH /api/notifications/read-all`

**Response `200`:**

| Campo | Tipo | Descripción |
|---|---|---|
| <!-- campo --> | <!-- tipo --> | <!-- descripción --> |

---

##### `GET /api/notifications/preferences`

**Response `200`:**

| Campo | Tipo | Descripción |
|---|---|---|
| <!-- campo --> | <!-- tipo --> | <!-- descripción --> |

---

##### `PATCH /api/notifications/preferences`

**Request Body:**

| Campo | Tipo | Restricciones | Obligatorio |
|---|---|---|---|
| <!-- campo --> | <!-- tipo --> | <!-- restricciones --> | <!-- sí/no --> |

**Response `200`:**

| Campo | Tipo | Descripción |
|---|---|---|
| <!-- campo --> | <!-- tipo --> | <!-- descripción --> |

---

#### Eventos Consumidos

| Evento | Origen | Descripción |
|---|---|---|
| <!-- evento --> | <!-- módulo --> | <!-- descripción --> |

---

## 10. Manejo de Errores

| Código HTTP | Tipo | Escenario | Excepción |
|---|---|---|---|
| <!-- código --> | <!-- tipo --> | <!-- escenario --> | <!-- excepción --> |

---

## 11. Fuentes Bibliográficas

- <!-- fuente -->

---

## 12. Historial de Revisión

| Versión | Fecha | Autor | Descripción |
|---|---|---|---|
| 1.0.0 | <!-- fecha --> | <!-- autor --> | Creación inicial del documento |