import Modelos.Casilla
import Modelos.Meta
import Modelos.Robot
import Modelos.Tablero
import Vistas.Componentes.sideBar
import Vistas.InicioScreen
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
    val tablero = Tablero(5, 5)
    val grafo: ILinkedWeightedVertexNotDirectedGraph = tablero.grafo

    // Establecer la meta (esquina inferior derecha)
    val metaVertex: Vertex = grafo.verticesList[4 * 5 + 4]// Fila 4, Columna 4
    metaVertex.info = Meta(4, 4)
    val metaCasilla = metaVertex.info as Meta

    // Actualizar pesos del grafo según la meta
    tablero.actualizarPesos(metaCasilla)

    // Crear el robot y ejecutar la simulación
    val robot = Robot(tablero, metaVertex)
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
//                    "Tablero" -> TableroScreen(robot, tablero)
                }
            }
        }
    }
}
