package Vistas

import Modelos.CasillaSeleccionada
import Modelos.Heuristica
import ViewModels.ReportesViewModel
import ViewModels.TableroViewModel
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
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun TableroScreen(
    viewModelTablero: TableroViewModel = viewModel(),
    viewModelReportes: ReportesViewModel = viewModel()

) {
    val state by viewModelTablero.state.collectAsState()

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
                    onClick = { viewModelTablero.setModoSeleccion("meta") },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (state.modoSeleccion == "meta") Color(0xFF4CAF50) else Color(0xFF1E88E5)
                    )
                ) {
                    Text(
                        text = "Seleccionar meta",
                        color = Color.White
                    )
                }

                Button(
                    onClick = { viewModelTablero.setModoSeleccion("inicio") },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (state.modoSeleccion == "inicio") Color(0xFF4CAF50) else Color(0xFF1E88E5)
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
                state.casillaMeta?.let { casilla ->
                    Text(
                        text = "meta casilla - Número: ${casilla.numero}, Coordenadas: (${casilla.coordenadas.first},${casilla.coordenadas.second})",
                        style = MaterialTheme.typography.overline,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
                state.casillaInicio?.let { casilla ->
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
                    var rows by remember { mutableStateOf(state.filas) }
                    var columns by remember { mutableStateOf(state.columnas) }

                    MatrixSizePicker(
                        rows = rows,
                        columns = columns,
                        onRowChange = { rows = it },
                        onColumnChange = { columns = it }
                    )

                    Button(
                        onClick = { viewModelTablero.actualizarDimensionesTablero(rows, columns) },
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
                        value = state.selectedNumber,
                        onValueChange = { viewModelTablero.setSelectedNumber(it) },
                        increment = 1,
                        minValue = 1,
                        maxValue = 100,
                        modifier = Modifier.fillMaxHeight(0.09f)
                    )

                    Button(
                        onClick = { viewModelTablero.desactivarCasillasAleatorias() },
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
                    text = "Matriz de tamaño: ${state.filas} x ${state.columnas}",
                    style = MaterialTheme.typography.overline,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                )

                repeat(state.filas) { fila ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(state.columnas) { columna ->
                            val peso =
                                (state.tablero?.buscarWVertexCoordenadas(fila, columna)?.weight as Heuristica).distancia
                            val posicionActual = Pair(fila, columna)
                            val trayectoria = state.trayectoria

                            state.casillasActivas?.let { casillasActivas ->
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(2.dp)
                                        .background(
                                            when {
                                                state.casillaMeta.coordenadas == posicionActual -> Color(199, 78, 78)
                                                state.casillaInicio.coordenadas == posicionActual -> Color(
                                                    154,
                                                    105,
                                                    214
                                                )

                                                posicionActual in trayectoria -> Color(237, 195, 107)
                                                posicionActual in casillasActivas -> Color(88, 157, 93)
                                                else -> Color.Gray
                                            },
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                        .clickable(
                                            enabled = state.modoSeleccion != null
                                        ) {
                                            val casillaSeleccionada = CasillaSeleccionada(
                                                numero = fila * state.columnas + columna,
                                                coordenadas = Pair(fila, columna)
                                            )
                                            viewModelTablero.seleccionarCasilla(casillaSeleccionada)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "$peso",
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
                    viewModelTablero.iniciarSimulacion()
                    println(
                        "La meta del robot seleccionado es:" + state.robot?.obtenerMeta()
                            .toString() + "con peso: " + (state.robot?.meta?.weight as Heuristica).distancia
                    )
                    viewModelTablero.state.value.robot?.let { viewModelReportes.addRobot(it) }
                },
                colors = ButtonDefaults.buttonColors(Color(255, 0, 51))
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
