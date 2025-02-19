package Vistas

import ViewModels.ReportesViewModel
import Vistas.Componentes.MyButtonWithTooltip
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ReporteScreen(reportesViewModel: ReportesViewModel = viewModel()) {
    val state by reportesViewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyButtonWithTooltip(
            texto = "Reporte 1",
            textoAlternativo = "Registrarse en un fichero la cantidad de pasos y las direcciones en que se dieron estos, así como si pudo llegar a la meta o no, además de las casillas salida y meta para cada simulación.\n",
            onClick = {}
        )
        MyButtonWithTooltip(
            texto = "Reporte 2",
            textoAlternativo = "Reporte en un fichero “.csv” ordenado por la cantidad de pasos todas las simulaciones que llegaron a la meta, poniendo casillas de entrada y salida, cantidad de pasos y fecha en que se realizó.\n",
            onClick = {}
        )
        MyButtonWithTooltip(
            texto = "Reporte 3",
            textoAlternativo = "Reporte en un fichero “.csv” ordenado por la distancia que faltó para llegar a la meta de todas las simulaciones que no llegaron, poniendo casillas de entrada y salida, distancia y fecha en que se realizó.\n",
            onClick = {}
        )
    }

}