package Vistas.Componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun leyenda(){
    // Añadir la leyenda
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Leyenda:",
            style = MaterialTheme.typography.overline,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Box(
                Modifier
                    .size(16.dp)
                    .background(Color(154, 105, 214)),
            )
            Text(text = "Posición del robot", color = Color.White, fontSize = 14.sp)

            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color(199, 78, 78)),
            )
            Text(text = "Casilla meta", color = Color.White, fontSize = 14.sp)

            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color(237, 195, 107)),
            )
            Text(text = "Trayectoria", color = Color.White, fontSize = 14.sp)

            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color(88, 157, 93)),
            )
            Text(text = "Casilla activa", color = Color.White, fontSize = 14.sp)

            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color.Gray),
            )
            Text(text = "Casilla inactiva", color = Color.White, fontSize = 14.sp)
        }
    }
}