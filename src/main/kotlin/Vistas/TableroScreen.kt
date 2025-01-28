package Vistas

import Modelos.Paso
import Modelos.Robot
import Modelos.Tablero
import Vistas.Componentes.MatrixSizePicker
import Vistas.Componentes.NumberPicker
import Vistas.Componentes.SelectInputFieldFiltrado
import Vistas.Componentes.Tablero
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun TableroScreen() {



    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            Text(
                text = "Configuracion de simulacion",
                style = MaterialTheme.typography.titleLarge,
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
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 14.sp,
                        )
//                        // Estado para el valor seleccionado
////                        var selectedNumber by remember { mutableStateOf(robot.pasosMaximos) }
//                        NumberPicker(
//                            value = selectedNumber,
//                            onValueChange = { selectedNumber = it },
//                            increment = 1,
//                            minValue = 1,
//                            maxValue = robot.pasosMaximos,
//                            modifier = Modifier.fillMaxHeight(0.05f).width(200.dp)
//                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        var rows by remember { mutableStateOf(3) }
                        var columns by remember { mutableStateOf(3) }

                        Text(
                            text = "Tamaño de la matriz",
                            style = MaterialTheme.typography.titleLarge,
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

                        Text(
                            text = "Matriz de tamaño: $rows x $columns",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 14.sp,
                        )

                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(modifier = Modifier.weight(1f)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Porcentaje de casillas inactivas",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                        // Estado para el valor seleccionado
                        var selectedNumber by remember { mutableStateOf(20) }
                        NumberPicker(
                            value = selectedNumber,
                            onValueChange = { selectedNumber = it },
                            increment = 5,
                            minValue = 1,
                            maxValue = 100
                        )
                    }
                }
            }

//            Tablero(tablero, robot, pasos)
        }
    }
}