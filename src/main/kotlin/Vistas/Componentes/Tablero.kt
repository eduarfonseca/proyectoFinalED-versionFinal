package Vistas.Componentes

import Modelos.Robot
import Modelos.Tablero
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//@Composable
//fun dynamicTablero(
//    robot: Robot, tablero: Tablero
//) {
//    val filas by remember{mutableStateOf(tablero.filas)}
//    val columnas by remember{mutableStateOf(tablero.columnas)}
//    val casillasActivas by remember{ mutableStateOf(tablero.obtenerCasillasActivas()) }
//    val posicionRobot by remember{ mutableStateOf(robot.obtenerPasoActual()) }
//    val meta by remember{mutableStateOf(tablero.obtenerMeta())}
//    val trayectoria by remember{ mutableStateOf(robot.obtenerTrayectoria()) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Matriz de tamaño: ${tableroState.filas} x ${tableroState.columnas}",
//            style = MaterialTheme.typography.overline,
//            fontWeight = FontWeight.Bold,
//            color = Color.White,
//            fontSize = 14.sp,
//            textAlign = TextAlign.Center,
//        )
//
//        repeat(filas) { fila ->
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                repeat(columnas) { columna ->
//                    val posicionActual = Pair(fila, columna)
//                    val trayectoria = trayectoriaState
//
//                    if (casillasActivas != null) {
//                        Box(
//                            modifier = Modifier
//                                .size(40.dp)
//                                .padding(2.dp)
//                                .background(
//                                    when {
//                                        casillaMeta?.coordenadas == posicionActual -> Color(199, 78, 78)
//                                        casillaInicio?.coordenadas == posicionActual -> Color(154, 105, 214)
//                                        posicionActual in trayectoria -> Color(237, 195, 107)
//                                        posicionActual in casillasActivas -> Color(88, 157, 93)
//                                        else -> Color.Gray
//                                    },
//                                    shape = RoundedCornerShape(4.dp)
//                                )
//                                .clickable(
//                                    enabled = modoSeleccion != null
//                                ) {
//                                    val casillaSeleccionada = crearCasillaSeleccionada(fila, columna)
//                                    when (modoSeleccion) {
//                                        "meta" -> {
//                                            casillaMeta = casillaSeleccionada
//                                            modoSeleccion = null
//                                        }
//
//                                        "inicio" -> {
//                                            casillaInicio = casillaSeleccionada
//                                            modoSeleccion = null
//                                        }
//                                    }
//                                },
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = "${fila},${columna}",
//                                fontSize = 10.sp,
//                                color = Color.White
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}


