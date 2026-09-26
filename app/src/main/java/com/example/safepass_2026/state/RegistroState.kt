package com.example.safepass_2026.state
import com.example.safepass_2026.model.Asistente

// Jerarquía cerrada que gestiona de manera determinista los estados de la interfaz.

sealed class RegistroState {
    // Estado inicial de la pantalla esperando datos
    object Idle : RegistroState()

    // Estado cuando los datos son válidos
    data class Success(val asistente: Asistente) : RegistroState()

    // Estado cuando existe un error o faltan datos
    data class Error(val mensaje: String) : RegistroState()
}