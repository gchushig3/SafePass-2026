# 🛡️ SafePass 2026: Sistema de Gestión de Check-in y Validación de Asistentes

[![Kotlin Version](https://img.shields.io/badge/Kotlin-2.0.0-blue.svg)](https://kotlinlang.org/)
[![Android API](https://img.shields.io/badge/API-36%20(Android%2016)-green.svg)](https://developer.android.com/)
[![Java Version](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)

Proyecto académico y examen práctico para la materia de **Programación Móvil (RDA-1)** en la **Pontificia Universidad Católica del Ecuador (PUCE)**.

---

## 📌 1. Descripción del Proyecto

En el marco del evento tecnológico **TechEvent 2026**, **SafePass 2026** es una solución móvil nativa en Android desarrollada para el personal de registro en el evento. La aplicación gestiona la validación de asistentes en tiempo real, garantizando el blindaje contra datos nulos o erróneos mediante los contratos de seguridad de Kotlin.

### 👥 Distribución de Roles y Responsabilidades

| Integrante | Rol Técnico | Responsabilidades Principales |
| :--- | :--- | :--- |
| **Integrante 1** | Architect / Data Model | Modelado inmutable (`Asistente`), jerarquía de estados (`RegistroState`) y gestión reactiva (`RegistroViewModel`). |
| **Integrante 2** | Logic & Validations | Extension functions (`esMayorDeEdad`), higher-order functions y blindaje contra nulos (`toIntOrNull`, `let`, `apply`). |
| **Integrante 3** | UI / Jetpack Compose | Diseño Edge-to-Edge (API 36), `Scaffold`, `Column` y evaluación exhaustiva `when` sobre `RegistroState`. |
| **Integrante 4** | DevOps & Documentation | Gestión de repositorio Git/GitHub, capturas de pruebas en emulador API 36 y consolidación del informe PDF (APA 7). |

---

## 🛠️ 2. Arquitectura y Componentes Técnicos

El proyecto sigue una arquitectura en capas limpia y orientada a la gestión determinista de estados.

```text
com.example.safepass_2026
 ├── model
 │    └── Asistente.kt          <-- Data Class Inmutable (Integrante 1)
 ├── state
 │    ├── RegistroState.kt      <-- Sealed Class para Estados UI (Integrante 1)
 │    └── RegistroViewModel.kt  <-- Gestor de Estado Reactivo (Integrante 1)
 ├── util
 │    └── Extensions.kt         <-- Extension Functions & Lambdas (Integrante 2)
 ├── ui.theme                   <-- Configuración de Estilo Material 3
 └── MainActivity.kt            <-- Interfaz con Jetpack Compose (Integrante 3)
```

## 🚀 3. Guía Paso a Paso para la Colaboración en el Equipo
Para garantizar un flujo de trabajo ordenado, evitar conflictos de fusión (merge conflicts) y mantener la trazabilidad requerida en el examen, el equipo utilizará el flujo de ramas (feature branches) y solicitudes de extracción (pull requests).

Paso 3.1: Configuración de Colaboradores (Integrante 1 - Admin)
1. Entrar al repositorio en GitHub: Settings ➔ Collaborators ➔ Add people.

2. Agregar las cuentas de GitHub de los Integrantes 2, 3 y 4 para otorgarles permisos de escritura.

Paso 3.2: Clonar el Repositorio (Integrantes 2, 3 y 4)
No crear un proyecto desde cero. En Android Studio seleccionar:

File ➔ New ➔ Project from Version Control... y pegar la URL del repositorio:

```https://github.com/tu_usuario/SafePass-2026.git```

Paso 3.3: Crear una Rama de Trabajo Local (Feature Branch)
Antes de realizar cualquier cambio en el código, cada integrante debe abrir la Terminal en Android Studio y crear su propia rama:

```text
# Integrante 2 (Lógica y Validaciones):
git checkout -b feature/logica-validacion

# Integrante 3 (Interfaz de Usuario Jetpack Compose):
git checkout -b feature/ui-compose

# Integrante 4 (DevOps & Documentación):
git checkout -b feature/documentacion-informe
```

Paso 3.4: Registrar Cambios con Commits Estandarizados
Guardar el progreso localmente asegurando el prefijo estandarizado feat: exigido por la rúbrica del examen:

```text
git add .
git commit -m "feat: <descripción descriptiva del aporte>"
```

Mensajes de Commit Requeridos por Integrante:
- Integrante 1: feat: data model y ui state manager para SafePass

- Integrante 2: ```feat: logic validation and scope functions```

- Integrante 3: ```feat: ui state integration and compose views```

- Integrante 4: ```feat: documentation and project structure setup```

Paso 3.5: Subir la Rama a GitHub y Crear Pull Request (PR)
Una vez finalizados y probados los cambios en la máquina local:

```git push -u origin feature/nombre-de-tu-rama```

1. Abrir la página del repositorio en GitHub.
2. Hacer clic en el botón emergente Compare & pull request.
3. Agregar una breve descripción de los cambios realizados y confirmar el Merge a la rama main.

Paso 3.6: Sincronizar el Proyecto Local
Antes de iniciar un nuevo bloque de trabajo, cada integrante debe actualizar su rama principal local con los avances del equipo:

```
git checkout main
git pull origin main
```

### 📱 4. Entorno de Desarrollo y Requisitos

- IDE Recomendado: Android Studio Ladybug / Jellyfish o superior.

- Lenguaje: Kotlin 2.0.0.

- JDK: JetBrains Runtime (JBR 21) embebido.

- Target SDK: API 36 (Android 16).

- Emulador Recomendado: Pixel 8 (API 36).

### 📄 5. Licencia y Créditos
Desarrollado como parte del Examen Práctico RDA-1 de Programación Móvil.

Docente: Juan Francisco Chafla, PhD.

Institución: Pontificia Universidad Católica del Ecuador (PUCE).

