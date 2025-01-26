import Modelos.Tablero
import Vistas.Componentes.macOSTitleBar
import Vistas.Componentes.sideBar
import Vistas.Componentes.tableroScreen
import Vistas.dashboardContent
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

@Composable
@Preview
fun App() {

    val tablero = Tablero(10, 10)

    // Inicializar el tablero con un 30% de casillas inactivas
    tablero.inicializarCasillasInactivas(30)

    // Mostrar el tablero en consola
    tablero.mostrarTablero()


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
                    "Inicio" -> dashboardContent()
                    "Tablero" -> tableroScreen()
//                    "Médicos" -> DoctorListContent()
//                    "Hospitales" -> HospitalListContent()
//                    "Buscar" -> SearchScreen()
//                    "Crear" -> CreateProfileForm()
//                    "Turnos" -> TurnosTablePreview()
//                    "Consultas" -> ConsultasTablePreview()
//                    "Departamentos" -> DepartamentoTablePreview()
//                    "Unidades" -> UnidadTablePreview()
                    // Agrega más casos aquí para las nuevas secciones
                }
            }
        }

    }
}

fun main() = application {
    val windowState = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(900.dp, 700.dp)
    )

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "Proyecto final ED",
        undecorated = true
    ) {
        Column {
            macOSTitleBar(windowState)
            App()
        }
    }
}
