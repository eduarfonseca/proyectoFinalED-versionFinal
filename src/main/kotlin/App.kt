import Modelos.Robot
import Modelos.Tablero
import Vistas.Componentes.sideBar
import Vistas.InicioScreen
import Vistas.TableroScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun App() {

    // Parámetros iniciales del Tablero y Robot
    val filas = 10
    val columnas = 10
    val seed = System.currentTimeMillis()
    val pasosMaximos = (filas * columnas) / 2

    // Instancia única de Tablero
    val tablero = remember { Tablero.getInstancia(filas, columnas, seed) }

    // Instancia única de Robot
    val robot = remember {
        val filaInicial = 0
        val columnaInicial = 0
        Robot.getInstancia(filaInicial, columnaInicial, pasosMaximos)
    }
    var selectedItem by remember { mutableStateOf("Inicio") }

    // Mostrar la estructura con la barra lateral y el contenido principal
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF121212))) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Sidebar
            sideBar(
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it }
            )

            // Main Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF121212))
                    .padding(16.dp)
            ) {
                when (selectedItem) {
                    "Inicio" -> InicioScreen()
                    "Tablero" -> TableroScreen(robot, tablero)
                }
            }
        }

    }

}
