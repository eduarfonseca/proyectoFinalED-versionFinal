import Modelos.Casilla
import Modelos.Robot
import Modelos.Tablero
import Navegacion.AppNavigation
import ViewModels.AppViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import cu.edu.cujae.ceis.graph.vertex.WeightedVertex


//@Composable
//fun App() {
//    // Crear un tablero de 5x5
//    val tablero = Tablero(10, 10)
//    val meta = tablero.grafo.verticesList[8] as WeightedVertex
//    val inicio = tablero.grafo.verticesList[1] as WeightedVertex
//
//    tablero.actualizarPesos(meta.info as Casilla, 20 )
//
//    val robot = Robot(tablero, meta, inicio)
//
//    var selectedItem by remember { mutableStateOf("Inicio") }
//
//    // Mostrar la estructura con la barra lateral y el contenido principal
//    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF121212))) {
//        Row(modifier = Modifier.fillMaxSize()) {
//            // Sidebar
//            sideBar(
//                selectedItem = selectedItem,
//                onItemSelected = { selectedItem = it }
//            )
//
//            // Main Content
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color(0xFF121212))
//                    .padding(16.dp)
//            ) {
//                when (selectedItem) {
//                    "Inicio" -> InicioScreen()
////                    "Tablero" -> TableroScreen(tablero)
//                    "Reportes" -> ReporteScreen()
//                }
//            }
//        }
//    }
//}

@Composable
fun App() {
    val appViewModel: AppViewModel = viewModel()

    // Crear un tablero de 5x5
    val tablero = Tablero(10, 10)
    val meta = tablero.grafo.verticesList[8] as WeightedVertex
    val inicio = tablero.grafo.verticesList[1] as WeightedVertex
    tablero.actualizarPesos(meta.info as Casilla, 20)

    val robot by remember { mutableStateOf(Robot(tablero, meta, inicio)) }

    AppNavigation(
        tablero = tablero,
        appViewModel = appViewModel,
        modifier = Modifier.fillMaxSize()
    )
}
