package Vistas

import Modelos.Robot
import Modelos.Tablero
import Vistas.Componentes.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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

    var filas by remember{mutableStateOf(tablero.filas)}
    var columnas by remember{mutableStateOf(tablero.columnas)}
    var casillasActivas by remember{ mutableStateOf(tablero.obtenerParesCasillasActiv()) }
    val posicionRobotState by remember{ mutableStateOf(robot.posicionActual) }
    val metaState by remember{mutableStateOf(robot.obtenerMeta())}
    val trayectoriaState by remember{ mutableStateOf(robot.gestorTrayectoria.obtenerPairsTrayectoria()) }

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            Text(
                text = "Configuracion de simulacion",
                style = MaterialTheme.typography.overline,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
                color = Color.White,
                fontSize = 18.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(2f)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Box(modifier = Modifier.weight(1f)) {
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Text(
//                            text = "Cantidad de pasos posibles",
//                            style = MaterialTheme.typography.overline,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.White,
//                            fontSize = 14.sp,
//                        )
//                        // Estado para el valor seleccionado
//                        var selectedNumber by remember { mutableStateOf(robot.pasosMaximos) }
//                        NumberPicker(
//                            value = selectedNumber,
//                            onValueChange = { selectedNumber = it },
//                            increment = 1,
//                            minValue = 1,
//                            maxValue = robot.pasosMaximos,
//                            modifier = Modifier.fillMaxHeight(0.05f).width(200.dp)
//                        )
////                        Button(
////                            onClick = { robotState. },
////                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5)),
////                            modifier = Modifier.padding(top = 16.dp)
////                        ) {
////                            Text(
////                                text = "Aplicar",
////                                fontSize = 18.sp,
////                                fontWeight = FontWeight.Medium,
////                                color = Color.White
////                            )
////                        }
//                    }
//                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Tamaño de la matriz",
                            style = MaterialTheme.typography.overline,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                        var rows by remember { mutableStateOf(filas) }
                        var columns by remember { mutableStateOf(columnas) }

                        MatrixSizePicker(
                            rows = rows,
                            columns = columns,
                            onRowChange = { rows = it },
                            onColumnChange = { columns = it }
                        )

                        Button(
                            onClick = {
                                filas = rows
                                columnas = columns
                                tablero.filas = rows
                                tablero.columnas = columns
                                casillasActivas = tablero.obtenerParesCasillasActiv()
                            },
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

//                        Button(
//                            onClick = {
//                                tablero.inicializarCasillasInactivas(selectedNumber)
//                                casillasActivas = tablero.obtenerCasillasActivas()
//                            },
//                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1E88E5)),
//                            modifier = Modifier.padding(top = 16.dp)
//                        ) {
//                            Text(
//                                text = "Aplicar",
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight.Medium,
//                                color = Color.White
//                            )
//                        }

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
                            val meta = Pair(metaState.x, metaState.y)
                            val posicionRobot = Pair(posicionRobotState.x, posicionRobotState.y)
                            val trayectoria = trayectoriaState
                            if (casillasActivas != null) {

                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(2.dp)
                                        .background(
                                            when {
                                                posicionActual == posicionRobot -> Color(154, 105, 214) // Posición del robot
                                                posicionActual == meta -> Color(199, 78, 78) // Casilla meta
                                                posicionActual in trayectoria -> Color(237, 195, 107) // Trayectoria
                                                posicionActual in casillasActivas -> Color(88, 157, 93) // Casilla activa
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