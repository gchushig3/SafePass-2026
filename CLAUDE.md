# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

SafePass 2026: single-module Android app (Kotlin 2.0.0, Jetpack Compose, Material 3, AGP 8.7.0) for validating event attendees at check-in. It is an academic exam project (PUCE, Programación Móvil RDA-1). Code comments and UI strings are in Spanish; keep that convention. compileSdk/targetSdk 36, minSdk 26, Java 21 (use the JBR bundled with Android Studio). Dependencies are managed through `gradle/libs.versions.toml`.

## Commands

Run from the repo root (on Windows PowerShell use `.\gradlew.bat`):

```bash
./gradlew assembleDebug                 # build the APK
./gradlew test                          # JVM unit tests (app/src/test)
./gradlew test --tests "com.example.safepass_2026.ExampleUnitTest"   # single test class
./gradlew connectedAndroidTest          # instrumented tests, needs emulator/device
```

No lint or formatter is configured beyond the Android defaults.

## Architecture

Package `com.example.safepass_2026`, layered around a deterministic UI state:

- `model/Asistente`: immutable data class; `edad` is nullable (`Int?`).
- `state/RegistroState`: sealed class (`Idle`, `Success(asistente)`, `Error(mensaje)`). The UI must handle it with an exhaustive `when`.
- `state/RegistroViewModel`: **not** an `androidx.lifecycle.ViewModel`. It is a plain class wrapping a Compose `mutableStateOf`, exposing a read-only `State<RegistroState>`. The lifecycle-viewmodel dependency is not in the project, so it does not survive configuration changes unless the owner is retained.
- `util/Extensions.kt`: safe-parsing and formatting extensions (`limpiarTexto`, `aEdadSegura`, `formatearNombre`, `Int.esMayorDeEdad`, `Asistente.resumen`) and the `EDAD_MINIMA` constant (18), the single place to change the age rule.
- `util/ValidadorAsistente.kt`: `ValidadorAsistente.validar(...)` takes raw nullable field text and always returns a `RegistroState` (never throws). `procesarRegistro(...)` is a higher-order wrapper that adds an optional extra rule (`reglaPrioridad`) and reports through the `onResultado` callback. `calcularPrecioFinal` is a separate lambda-based price helper.
- `MainActivity.kt`: edge-to-edge Compose entry point. It is still the template `Greeting` placeholder on this branch (`feature/ui-compose`); the real UI should wire text fields to `procesarRegistro` and render `RegistroViewModel.uiState`.

## Workflow conventions

From the README: work on per-role feature branches (`feature/logica-validacion`, `feature/ui-compose`, `feature/documentacion-informe`), open PRs into `main`, and use commit messages prefixed `feat:`, as required by the exam rubric.
