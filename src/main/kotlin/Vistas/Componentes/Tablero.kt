package Vistas.Componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun dynamicTablero(
    filas: Int,
    columnas: Int,
    casillasActivas: List<Pair<Int, Int>>,
    posicionRobot: Pair<Int, Int>,
    meta: Pair<Int, Int>,
    trayectoria: List<Pair<Int, Int>>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(filas) { fila ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(columnas) { columna ->
                    val posicionActual = Pair(fila, columna)
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(2.dp)
                            .background(
                                when {
                                    posicionActual == posicionRobot -> Color.Blue // Posición del robot
                                    posicionActual == meta -> Color.Red // Casilla meta
                                    posicionActual in trayectoria -> Color.Yellow // Trayectoria
                                    posicionActual in casillasActivas -> Color.Green // Casilla activa
                                    else -> Color.Gray // Casilla inactiva
                                },
                                shape = RoundedCornerShape(4.dp)
                            ),
//                        contentAlignment = Alignment.Center
                    )
//                    {
//                        // Mostrar coordenadas solo como referencia
//                        Text(
//                            text = "${fila + 1},${columna + 1}",
//                            fontSize = 10.sp,
//                            color = Color.White
//                        )
//                    }
                }
            }
        }
    }
}

@Composable
fun tableroScreen() {
    val filas = 10
    val columnas = 10
    val casillasActivas = listOf(
        Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0), Pair(4, 0),
        Pair(5, 0), Pair(6, 0), Pair(7, 0), Pair(8, 0), Pair(9, 9)
    )
    val posicionRobot = Pair(2, 0) // Posición actual del robot
    val meta = Pair(9, 9) // Meta
    val trayectoria = listOf(Pair(0, 0), Pair(1, 0), Pair(2, 0)) // Pasos recorridos

    dynamicTablero(
        filas = filas,
        columnas = columnas,
        casillasActivas = casillasActivas,
        posicionRobot = posicionRobot,
        meta = meta,
        trayectoria = trayectoria
    )
}

