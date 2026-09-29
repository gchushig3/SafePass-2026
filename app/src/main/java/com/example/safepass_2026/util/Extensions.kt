package com.example.safepass_2026.util

import com.example.safepass_2026.model.Asistente

// Edad mínima para ingresar al evento. Si en la oral te piden cambiarla, solo se cambia aquí.
const val EDAD_MINIMA = 18

/**
 * Extension function sobre Int.
 * Devuelve true si la edad es igual o mayor a la edad mínima del evento.
 */
fun Int.esMayorDeEdad(): Boolean = this >= EDAD_MINIMA

/**
 * Extension function sobre String? (texto que puede ser nulo).
 * Quita espacios al inicio y al final. Si el texto es nulo, devuelve "".
 */
fun String?.limpiarTexto(): String = this?.trim() ?: ""

/**
 * Convierte el texto de la edad en Int de forma segura.
 * "25" -> 25 | "abc" -> null | "" -> null | null -> null
 */
fun String?.aEdadSegura(): Int? = this?.trim()?.toIntOrNull()

/**
 * Convierte un texto de dinero en Double de forma segura.
 * Acepta coma o punto decimal: "12,50" -> 12.5 | "diez" -> null
 */
fun String?.aMontoSeguro(): Double? = this?.trim()?.replace(',', '.')?.toDoubleOrNull()

/**
 * Formatea un nombre: "  aLi   BASANTES " -> "Ali Basantes"
 */
fun String.formatearNombre(): String =
    this.trim()
        .split(" ")
        .filter { it.isNotBlank() }
        .joinToString(" ") { palabra ->
            palabra.lowercase().replaceFirstChar { it.uppercase() }
        }

/**
 * Extension function sobre Asistente.
 * Arma el resumen que la pantalla muestra en el estado Success (plantillas de cadena $).
 */
fun Asistente.resumen(): String {
    val textoEdad = edad?.let { "$it años" } ?: "No registrada"
    return "Nombre: $nombre\nEdad: $textoEdad\nTipo de entrada: $tipoEntrada"
}