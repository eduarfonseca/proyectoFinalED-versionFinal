package Navegacion

import Modelos.Tablero
import ViewModels.AppViewModel
import Vistas.Componentes.ReporteScreen
import Vistas.Componentes.sideBar
import Vistas.InicioScreen
import Vistas.TableroScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation(
    tablero: Tablero,
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel()
) {
    val navController = rememberNavController()
    val currentScreen by appViewModel.currentScreen.collectAsState()

    val navigationActions = remember(navController, appViewModel) {
        NavigationActions(navController, appViewModel)
    }

    Box(modifier = modifier.fillMaxSize().background(Color(0xFF121212))) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Sidebar actualizado
            sideBar(
                selectedItem = when(currentScreen) {
                    Screen.Inicio -> "Inicio"
                    Screen.Tablero -> "Tablero"
                    Screen.Reportes -> "Reportes"
                },
                onItemSelected = { route ->
                    when(route) {
                        "Inicio" -> navigationActions.navigateTo(Screen.Inicio)
                        "Tablero" -> navigationActions.navigateTo(Screen.Tablero)
                        "Reportes" -> navigationActions.navigateTo(Screen.Reportes)
                    }
                }
            )

            NavHost(
                navController = navController,
                startDestination = Screen.Inicio.route,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF121212))
                    .padding(16.dp)
            ) {
                composable(Screen.Inicio.route) {
                    InicioScreen()
                }

                composable(Screen.Tablero.route) {
                    TableroScreen(tablero = tablero)
                }

                composable(Screen.Reportes.route) {
                    ReporteScreen()
                }
            }
        }
    }
}