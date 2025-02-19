package Vistas.Componentes

import ViewModels.TableroViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ReporteScreen(tableroViewModel: TableroViewModel = viewModel()) {
    val state by tableroViewModel.state.collectAsState()

}