package Vistas

import Modelos.Paso
import Modelos.Robot
import Modelos.Tablero
import Vistas.Componentes.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun TableroScreen(robot: Robot, tablero: Tablero) {

    var robotState by remember { mutableStateOf(robot) }
    var tableroState by remember { mutableStateOf(tablero) }

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            Text(
                text = "Configuracion de simulacion",
                style = MaterialTheme.typography.overline,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(2f)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Cantidad de pasos posibles",
                            style = MaterialTheme.typography.overline,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                        // Estado para el valor seleccionado
                        var selectedNumber by remember { mutableStateOf(robot.pasosMaximos) }
                        NumberPicker(
                            value = selectedNumber,
                            onValueChange = { selectedNumber = it },
                            increment = 1,
                            minValue = 1,
                            maxValue = robot.pasosMaximos,
                            modifier = Modifier.fillMaxHeight(0.05f).width(200.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        var rows by remember { mutableStateOf(tablero.filas) }
                        var columns by remember { mutableStateOf(tablero.columnas) }

                        Text(
                            text = "Tamaño de la matriz",
                            style = MaterialTheme.typography.overline,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 14.sp,
                        )

                        MatrixSizePicker(
                            rows = rows,
                            columns = columns,
                            onRowChange = { rows = it },
                            onColumnChange = { columns = it }
                        )

                        Button(
                            onClick = { tablero.filas = rows; tablero.columnas = columns },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5)),
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text(
                                text = "Aplicar",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }

                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Porcentaje de casillas inactivas",
                            style = MaterialTheme.typography.overline,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                        // Estado para el valor seleccionado
                        var selectedNumber by remember { mutableStateOf(20) }
                        NumberPicker(
                            value = selectedNumber,
                            onValueChange = { selectedNumber = it },
                            increment = 1,
                            minValue = 1,
                            maxValue = 100
                        )

                        Button(
                            onClick = { tablero.inicializarCasillasInactivas(selectedNumber) },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5)),
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text(
                                text = "Aplicar",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }

                    }


                }
            }
            Text(
                text = "Matriz de tamaño: ${tablero.filas} x ${tablero.columnas}",
                style = MaterialTheme.typography.overline,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 14.sp,
            )

            dynamicTablero(robotState, tableroState)
        }
    }
}


//@Composable
//fun Tablero(tablero: Tablero, robot: Robot) {
//    val filas = tablero.filas
//    val columnas = tablero.columnas
//
//
//    val casillasActivas = tablero.obtenerCasillasActivas()
//    val meta = tablero.obtenerMeta() // Meta
//    val trayectoria = robot.obtenerTrayectoria(pasos) // Pasos recorridos
//    val posicionRobot = trayectoria[trayectoria.size - 1] // Posición actual del robot
//
//    dynamicTablero(
//        filas = filas,
//        columnas = columnas,
//        casillasActivas = casillasActivas,
//        posicionRobot = posicionRobot,
//        meta = meta,
//        trayectoria = trayectoria
//    )
//}