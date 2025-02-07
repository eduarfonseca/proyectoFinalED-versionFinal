import Modelos.Robot
import Modelos.Tablero
import Vistas.Componentes.macOSTitleBar
import Vistas.Componentes.sideBar
import Vistas.InicioScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val windowState = rememberWindowState(
        position = WindowPosition(Alignment.Center),
        size = DpSize(900.dp, 700.dp)
    )

//    prueba()

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
