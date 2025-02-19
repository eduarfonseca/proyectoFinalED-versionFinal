import Navegacion.AppNavigation
import ViewModels.AppViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun App() {
    val viewModel: AppViewModel = viewModel()

    AppNavigation(
        appViewModel = viewModel,
        modifier = Modifier.fillMaxSize()
    )
}
