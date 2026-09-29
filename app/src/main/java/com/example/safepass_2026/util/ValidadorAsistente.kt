package com.example.safepass_2026.util

import com.example.safepass_2026.model.Asistente
import com.example.safepass_2026.state.RegistroState

/**
 * Clase auxiliar con propiedades var.
 * Sirve para configurar los datos paso a paso con apply
 * y al final construir un Asistente inmutable (todo val).
 */
class AsistenteBuilder {
    var nombre: String = ""
    var edad: Int? = null
    var tipoEntrada: String = ""

    fun construir(): Asistente = Asistente(
        nombre = nombre,
        edad = edad,
        tipoEntrada = tipoEntrada
    )
}

/**
 * Capa de validación de datos de entrada.
 * Recibe los textos tal como llegan de los campos de la pantalla
 * y devuelve siempre un RegistroState (nunca lanza una excepción).
 */
object ValidadorAsistente {

    fun validar(
        nombreTexto: String?,
        edadTexto: String?,
        tipoEntradaTexto: String?
    ): RegistroState = run {
        // run agrupa toda la validación y devuelve el último valor del bloque
        val nombre = nombreTexto.limpiarTexto()
        val tipoEntrada = tipoEntradaTexto.limpiarTexto()
        val edad = edadTexto.aEdadSegura()

        when {
            nombre.isEmpty() ->
                RegistroState.Error("El nombre es obligatorio.")

            edadTexto.limpiarTexto().isEmpty() ->
                RegistroState.Error("La edad es obligatoria.")

            edad == null ->
                RegistroState.Error("La edad debe ser un número entero. Escribiste: \"${edadTexto.limpiarTexto()}\".")

            !edad.esMayorDeEdad() ->
                RegistroState.Error("Acceso denegado: el asistente tiene $edad años y la edad mínima es $EDAD_MINIMA.")

            tipoEntrada.isEmpty() ->
                RegistroState.Error("El tipo de entrada es obligatorio.")

            else -> {
                // apply configura el objeto y devuelve el mismo objeto
                val asistente = AsistenteBuilder().apply {
                    this.nombre = nombre.formatearNombre()
                    this.edad = edad
                    this.tipoEntrada = tipoEntrada
                }.construir()

                RegistroState.Success(asistente)
            }
        }
    }
}

/**
 * Higher-order function: recibe lambdas como parámetros.
 *
 * reglaPrioridad: validación extra opcional. Devuelve un mensaje de error o null si todo está bien.
 * onResultado: lo que la pantalla hace con el estado final (actualizar el ViewModel).
 */
fun procesarRegistro(
    nombreTexto: String?,
    edadTexto: String?,
    tipoEntradaTexto: String?,
    reglaPrioridad: (Asistente) -> String? = { null },
    onResultado: (RegistroState) -> Unit
) {
    val estadoBase = ValidadorAsistente.validar(nombreTexto, edadTexto, tipoEntradaTexto)

    val estadoFinal = if (estadoBase is RegistroState.Success) {
        // let: solo se ejecuta si la regla devolvió un mensaje (no nulo)
        reglaPrioridad(estadoBase.asistente)?.let { mensaje -> RegistroState.Error(mensaje) }
            ?: estadoBase
    } else {
        estadoBase
    }

    onResultado(estadoFinal)
}

/**
 * Higher-order function para el "descuento de reserva".
 * Recibe el precio como texto y una lambda que calcula el precio final.
 * Devuelve null si el precio no es un número válido o es negativo.
 */
fun calcularPrecioFinal(
    precioTexto: String?,
    descuento: (Double) -> Double
): Double? =
    precioTexto.aMontoSeguro()
        ?.takeIf { it >= 0.0 }
        ?.let { precio -> descuento(precio).coerceAtLeast(0.0) }