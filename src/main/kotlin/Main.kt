import Modelos.Robot
import Modelos.Tablero
import Vistas.Componentes.macOSTitleBar
import Vistas.Componentes.sideBar
import Vistas.InicioScreen
import Vistas.TableroScreen
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

//fun prueba(){
//    // Dimensiones del tablero y parámetros iniciales
//    val filas = 10
//    val columnas = 10
//    val semilla = System.currentTimeMillis()
//
//    val porcentajeInactivas = 30
//    val pasosMaximos = (filas * columnas) / 2
//
//    // Crear el tablero con las configuraciones iniciales
//    val tablero = Tablero(filas, columnas, semilla)
//    tablero.inicializarCasillasInactivas(porcentajeInactivas)
//    tablero.mostrarTablero()
//
//    // Obtener las coordenadas iniciales y de la meta
//    val filaInicial = 0
//    val columnaInicial = 0
//    val meta = tablero.obtenerMeta()
//    val metaFila = meta.first
//    val metaColumna = meta.second
//
//    println("\nMeta establecida en: ($metaFila, $metaColumna)\n")
//
//    // Crear un robot en la posición inicial
//    val robot = Robot(filaInicial, columnaInicial, pasosMaximos)
//
//    // Ejecutar el algoritmo de escalador de colinas
//    val pasos = robot.moverHaciaMeta(tablero.getTablero(), metaFila, metaColumna)
//
//    // Mostrar el resultado de cada paso
//    println("Trayectoria del robot:")
//    for (paso in pasos) {
//        println(paso)
//    }
//
//    // Mostrar si se alcanzó la meta o no
//    if (pasos.last().mensaje == "Meta alcanzada.") {
//        println("\nEl robot alcanzó la meta con éxito.")
//    } else {
//        println("\nEl robot no pudo alcanzar la meta.")
//    }

//}
