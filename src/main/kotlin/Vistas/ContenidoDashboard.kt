package Vistas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun dashboardContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFF121212)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título del proyecto
        Text(
            text = "Simulación de Movimiento de Robot",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Descripción breve del proyecto
        Text(
            text = "Este proyecto simula el movimiento de un robot sobre un tablero interactivo.\n" +
                    "El objetivo es que el robot alcance una meta desde un punto de partida aleatorio,\n" +
                    "siguiendo las reglas establecidas y empleando el algoritmo escalador de colinas.\n" +
                    "\nExplora las funcionalidades para visualizar el tablero, simular movimientos\n" +
                    "y generar reportes de las simulaciones realizadas.",
            fontSize = 16.sp,
            color = Color(0xFFB0BEC5),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Botón para comenzar
        Button(
            onClick = { /* Acción al presionar el botón, como navegar a otra pantalla */ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5)),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Comenzar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}




