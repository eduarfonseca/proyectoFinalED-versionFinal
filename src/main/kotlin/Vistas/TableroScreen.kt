package Vistas

import Modelos.Robot
import Modelos.Tablero
import Vistas.Componentes.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cu.edu.cujae.ceis.graph.vertex.WeightedVertex

@Composable
fun TableroScreen(tablero: Tablero) {
    var filas by remember { mutableStateOf(tablero.filas) }
    var columnas by remember { mutableStateOf(tablero.columnas) }
    var casillasActivas by remember { mutableStateOf(tablero.obtenerParesCasillasActiv()) }
    var tableroState by remember { mutableStateOf(tablero) }
    var selectedNumber by remember { mutableStateOf(0) }
    // Estados para las dos casillas seleccionadas usando la nueva clase
    var casillaMeta by remember { mutableStateOf<CasillaSeleccionada?>(CasillaSeleccionada(1, Pair(0,1))) }
    var casillaInicio by remember { mutableStateOf<CasillaSeleccionada?>(CasillaSeleccionada(0, Pair(0,0))) }

    var modoSeleccion by remember { mutableStateOf<String?>(null) }

    // Modificar estos estados para que sean mutables y observables
    var meta: WeightedVertex? by remember { mutableStateOf(tablero.grafo.verticesList[0] as WeightedVertex?) }
    var robotState by remember { mutableStateOf(Robot(tablero, meta)) }
    var posicionRobotState by remember { mutableStateOf(robotState.posicionActual) }
    var trayectoriaState by remember { mutableStateOf(robotState.gestorTrayectoria.obtenerPairsTrayectoria()) }
    val metaState by remember { mutableStateOf(robotState.obtenerMeta()) }

    // Efecto para actualizar la trayectoria cuando cambie el robot
    LaunchedEffect(robotState) {
        casillasActivas = tableroState.obtenerParesCasillasActiv()
        posicionRobotState = robotState.posicionActual
        trayectoriaState = robotState.gestorTrayectoria.obtenerPairsTrayectoria()
        tableroState = Tablero(filas,columnas)
        robotState.setPosicionActual(tablero.grafo.verticesList[casillaInicio?.numero!!] as WeightedVertex)
        meta = tableroState.grafo.verticesList[casillaMeta?.numero!!] as WeightedVertex?
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        modoSeleccion = if (modoSeleccion == "meta") null else "meta"
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (modoSeleccion == "meta") Color(0xFF4CAF50) else Color(0xFF1E88E5)
                    )
                ) {
                    Text(
                        text = "Seleccionar meta",
                        color = Color.White
                    )
                }

                Button(
                    onClick = {
                        modoSeleccion = if (modoSeleccion == "inicio") null else "inicio"
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (modoSeleccion == "inicio") Color(0xFF4CAF50) else Color(0xFF1E88E5)
                    )
                ) {
                    Text(
                        text = "Seleccionar casilla de inicio",
                        color = Color.White
                    )
                }
            }


            // Mostrar información de las casillas seleccionadas
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                casillaMeta?.let { casilla ->
                    Text(
                        text = "meta casilla - Número: ${casilla.numero}, Coordenadas: (${casilla.coordenadas.first},${casilla.coordenadas.second})",
                        style = MaterialTheme.typography.overline,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
                casillaInicio?.let { casilla ->
                    Text(
                        text = "inicio casilla - Número: ${casilla.numero}, Coordenadas: (${casilla.coordenadas.first},${casilla.coordenadas.second})",
                        style = MaterialTheme.typography.overline,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(2f)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Tamaño de la matriz",
                        style = MaterialTheme.typography.overline,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    var rows by remember { mutableStateOf(filas) }
                    var columns by remember { mutableStateOf(columnas) }

                    MatrixSizePicker(
                        rows = rows,
                        columns = columns,
                        onRowChange = { rows = it },
                        onColumnChange = { columns = it },
//                        modifier = Modifier.fillMaxHeight(0.3f).fillMaxWidth(0.3f)
                    )

                    Button(
                        onClick = {
                            filas = rows
                            columnas = columns
                            tableroState = Tablero(filas, columnas)
                            tableroState.actualizarPesos(metaState, selectedNumber) // Utilizar selectedNumber
                            casillasActivas = tableroState.obtenerParesCasillasActiv()
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

                Spacer(modifier = Modifier.width(10.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Porcentaje de casillas inactivas",
                        style = MaterialTheme.typography.overline,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    NumberPicker(
                        value = selectedNumber,
                        onValueChange = { selectedNumber = it },
                        increment = 1,
                        minValue = 1,
                        maxValue = 100,
                        modifier = Modifier.fillMaxHeight(0.09f)
                    )

                    Button(
                        onClick = {
                            tableroState.desactivarCasillasAleatoriamente(selectedNumber)
                            casillasActivas = tableroState.obtenerParesCasillasActiv() // Actualizar casillas activas
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Matriz de tamaño: ${tableroState.filas} x ${tableroState.columnas}",
                    style = MaterialTheme.typography.overline,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                )

                repeat(filas) { fila ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(columnas) { columna ->
                            val posicionActual = Pair(fila, columna)
                            val trayectoria = trayectoriaState

                            if (casillasActivas != null) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(2.dp)
                                        .background(
                                            when {
                                                casillaMeta?.coordenadas == posicionActual -> Color(199, 78, 78)
                                                casillaInicio?.coordenadas == posicionActual -> Color(154, 105, 214)
                                                posicionActual in trayectoria -> Color(237, 195, 107)
                                                posicionActual in casillasActivas -> Color(88, 157, 93)
                                                else -> Color.Gray
                                            },
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                        .clickable(
                                            enabled = modoSeleccion != null
                                        ) {
                                            val casillaSeleccionada = crearCasillaSeleccionada(fila, columnas, columna)
                                            when (modoSeleccion) {
                                                "meta" -> {
                                                    casillaMeta = casillaSeleccionada
                                                    modoSeleccion = null
                                                }

                                                "inicio" -> {
                                                    casillaInicio = casillaSeleccionada
                                                    modoSeleccion = null
                                                }
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${fila},${columna}",
                                        fontSize = 10.sp,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Button(
                onClick = {
                    robotState = Robot(tableroState,meta)
                    robotState.mover()
                },
                colors = ButtonDefaults.buttonColors(
                    Color(255, 0, 51)
                )
            ) {
                Text(
                    text = "Iniciar simulacion",
                    color = Color.White
                )
            }

            leyenda()
        }
    }
}

fun convertirPosicionANumero(fila: Int, columnas: Int, columna: Int): Int {
    return fila * columnas + columna
}

fun crearCasillaSeleccionada(fila: Int, columnas: Int, columna: Int): CasillaSeleccionada {
    return CasillaSeleccionada(
        numero = convertirPosicionANumero(fila, columnas, columna),
        coordenadas = Pair(fila, columna)
    )
}

// Clase para representar una casilla seleccionada
data class CasillaSeleccionada(
    val numero: Int,
    val coordenadas: Pair<Int, Int>
)

