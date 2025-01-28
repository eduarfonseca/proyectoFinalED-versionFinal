package Vistas.Componentes

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun NumberPicker(
    value: Int = 0,
    onValueChange: (Int) -> Unit,
    increment: Int = 1,
    minValue: Int = 0,
    maxValue: Int = 100,
    modifier: Modifier = Modifier,
) {
    var currentValue by remember { mutableStateOf(value.coerceIn(minValue, maxValue)) }

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center).background(Color(16, 78, 146), RoundedCornerShape(8.dp))
        ) {
            IconButton(onClick = {
                if (currentValue > minValue) {
                    currentValue -= increment
                    onValueChange(currentValue)
                }
            }) {
                Icon(imageVector = Icons.Filled.Remove, contentDescription = "Remove")
            }

            Text(text = currentValue.toString(), modifier = Modifier.padding(horizontal = 6.dp))

            IconButton(onClick = {
                if (currentValue < maxValue) {
                    currentValue += increment
                    onValueChange(currentValue)
                }
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    }
}

@Composable
fun MatrixSizePicker(
    rows: Int = 3,
    columns: Int = 3,
    onRowChange: (Int) -> Unit,
    onColumnChange: (Int) -> Unit,
) {
    var currentRows by remember { mutableStateOf(rows) }
    var currentColumns by remember { mutableStateOf(columns) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Filas: ", style = MaterialTheme.typography.bodyLarge, fontSize = 14.sp, color = Color.White)
        NumberPicker(
            value = currentRows,
            onValueChange = {
                currentRows = it
                onRowChange(it)
            },
            minValue = 1,
            maxValue = 100,
            modifier = Modifier.fillMaxHeight(0.05f).width(200.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Columnas: ", style = MaterialTheme.typography.bodyLarge, fontSize =  14.sp, color = Color.White)
        NumberPicker(
            value = currentColumns,
            onValueChange = {
                currentColumns = it
                onColumnChange(it)
            },
            minValue = 1,
            maxValue = 100,
            modifier = Modifier.fillMaxHeight(0.05f).width(200.dp)
        )
    }
}



