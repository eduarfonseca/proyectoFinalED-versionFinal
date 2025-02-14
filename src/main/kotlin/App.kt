import Modelos.Casilla
import Modelos.Meta
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
import cu.edu.cujae.ceis.graph.interfaces.ILinkedWeightedVertexNotDirectedGraph
import cu.edu.cujae.ceis.graph.vertex.Vertex
import cu.edu.cujae.ceis.graph.vertex.WeightedVertex


@Composable
fun App() {
    // Crear un tablero de 5x5
    val tablero = Tablero(10, 10)
    val meta = tablero.grafo.verticesList[8] as WeightedVertex
    val inicio = tablero.grafo.verticesList[1] as WeightedVertex

    tablero.actualizarPesos(meta.info as Casilla, 20 )

    val robot = Robot(tablero, meta, inicio)
    robot.mover()

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
//                    "Tablero" -> TableroScreen(tablero)
                }
            }
        }
    }
}
