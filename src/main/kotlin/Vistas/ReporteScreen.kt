package Vistas

import ViewModels.ReportesViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ReporteScreen(reportesViewModel: ReportesViewModel = viewModel()) {
    val state by reportesViewModel.state.collectAsState()
    val size = state.listaRobots.size

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 0 until size) {
            Text(
                text = state.listaRobots[i].obtenerMeta().toString(),
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}