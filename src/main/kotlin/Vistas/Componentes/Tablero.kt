package Vistas.Componentes

import Modelos.Paso
import Modelos.Robot
import Modelos.Tablero
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                                    posicionActual == posicionRobot -> Color(154,105,214) // Posición del robot
                                    posicionActual == meta -> Color(199,78,78) // Casilla meta
                                    posicionActual in trayectoria -> Color(237,195,107) // Trayectoria
                                    posicionActual in casillasActivas -> Color(88,157,93) // Casilla activa
                                    else -> Color.Gray // Casilla inactiva
                                },
                                shape = RoundedCornerShape(4.dp)
                            ),
                        contentAlignment = Alignment.Center
                    )
                    {
                        // Mostrar coordenadas solo como referencia
                        Text(
                            text = "${fila + 1},${columna + 1}",
                            fontSize = 10.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Tablero(tablero: Tablero, robot: Robot, pasos: List<Paso>) {
    val filas = tablero.filas
    val columnas = tablero.columnas


    val casillasActivas = tablero.obtenerCasillasActivas()
    val meta = tablero.obtenerMeta() // Meta
    val trayectoria = robot.obtenerTrayectoria(pasos) // Pasos recorridos
    val posicionRobot = trayectoria[trayectoria.size-1] // Posición actual del robot

    dynamicTablero(
        filas = filas,
        columnas = columnas,
        casillasActivas = casillasActivas,
        posicionRobot = posicionRobot,
        meta = meta,
        trayectoria = trayectoria
    )
}

