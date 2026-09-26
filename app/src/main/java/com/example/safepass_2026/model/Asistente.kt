package com.example.safepass_2026.model

// Modelo de datos inmutable que representa a un asistente registrado en SafePass 2026.

data class Asistente(
    val nombre: String,
    val edad: Int?,
    val tipoEntrada: String
)
