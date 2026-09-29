package com.example.safepass_2026.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.safepass_2026.state.RegistroState
import com.example.safepass_2026.state.RegistroViewModel
import com.example.safepass_2026.util.procesarRegistro
import com.example.safepass_2026.util.resumen

/**
 * Pantalla principal de SafePass 2026.
 * Scaffold da la estructura (barra superior + contenido) y Column apila los elementos.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafePassScreen(viewModel: RegistroViewModel = remember { RegistroViewModel() }) {
    // Texto de los campos: se conserva entre recomposiciones con remember
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var tipoEntrada by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("SafePass 2026", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Registro de asistentes",
                style = MaterialTheme.typography.titleLarge
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = edad,
                onValueChange = { edad = it },
                label = { Text("Edad") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = tipoEntrada,
                onValueChange = { tipoEntrada = it },
                label = { Text("Tipo de entrada (General, VIP...)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {
                        // La lógica vive en util/: la pantalla solo delega y actualiza el estado
                        procesarRegistro(nombre, edad, tipoEntrada) { estado ->
                            when (estado) {
                                is RegistroState.Success -> viewModel.registrarExitoso(estado.asistente)
                                is RegistroState.Error -> viewModel.registrarError(estado.mensaje)
                                RegistroState.Idle -> viewModel.reiniciarEstado()
                            }
                        }
                    }
                ) { Text("Registrar") }

                OutlinedButton(
                    onClick = {
                        nombre = ""
                        edad = ""
                        tipoEntrada = ""
                        viewModel.reiniciarEstado()
                    }
                ) { Text("Limpiar") }
            }

            EstadoRegistro(viewModel.uiState.value)
        }
    }
}

/**
 * Renderiza la tarjeta según el estado.
 * El when es exhaustivo sobre la sealed class: no hay rama else.
 */
@Composable
private fun EstadoRegistro(estado: RegistroState) {
    when (estado) {
        RegistroState.Idle -> TarjetaEstado(
            titulo = "Esperando registro",
            detalle = "Ingresa los datos del asistente y presiona \"Registrar\".",
            colorFondo = MaterialTheme.colorScheme.surfaceVariant,
            colorTexto = MaterialTheme.colorScheme.onSurfaceVariant
        )

        is RegistroState.Success -> TarjetaEstado(
            titulo = "Registro exitoso",
            detalle = estado.asistente.resumen(),
            colorFondo = Color(0xFFC8E6C9),
            colorTexto = Color(0xFF1B5E20)
        )

        is RegistroState.Error -> TarjetaEstado(
            titulo = "Error de validación",
            detalle = estado.mensaje,
            colorFondo = MaterialTheme.colorScheme.errorContainer,
            colorTexto = MaterialTheme.colorScheme.onErrorContainer
        )
    }
}

@Composable
private fun TarjetaEstado(
    titulo: String,
    detalle: String,
    colorFondo: Color,
    colorTexto: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colorFondo, contentColor = colorTexto)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(text = detalle, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
