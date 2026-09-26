package com.example.safepass_2026.state
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

/**
 * Gestor del estado de la interfaz de usuario (UI State Manager).
 * Mantiene el estado protegido y expone una versión de solo lectura.
 */
class RegistroViewModel {
    // Estado privado modificable únicamente desde esta clase
    private val _uiState = mutableStateOf<RegistroState>(RegistroState.Idle)

    // Estado público de solo lectura expuesto a la interfaz de Compose
    val uiState: State<RegistroState> = _uiState

    // Actualiza el estado a Success tras procesar un Asistente válido.
    fun registrarExitoso(asistente: com.example.safepass_2026.model.Asistente) {
        _uiState.value = RegistroState.Success(asistente)
    }

    // Actualiza el estado a Error en caso de falla de validación.
    fun registrarError(mensaje: String) {
        _uiState.value = RegistroState.Error(mensaje)
    }

    // Reinicia el estado al punto inicial.
    fun reiniciarEstado() {
        _uiState.value = RegistroState.Idle
    }
}