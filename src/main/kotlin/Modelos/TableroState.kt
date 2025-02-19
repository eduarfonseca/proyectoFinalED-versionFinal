package Modelos

import cu.edu.cujae.ceis.graph.vertex.WeightedVertex

data class TableroState(
    val filas: Int = 10,
    val columnas: Int = 10,
    val casillasActivas: MutableList<Pair<Int, Int>>? = null,
    val tablero: Tablero? = null,
    val selectedNumber: Int = 0,
    val casillaMeta: Vistas.CasillaSeleccionada = CasillaSeleccionada(1, Pair(0,1)),
    val casillaInicio: Vistas.CasillaSeleccionada = CasillaSeleccionada(0, Pair(0,0)),
    val modoSeleccion: String? = null,
    val meta: WeightedVertex? = null,
    val inicio: WeightedVertex? = null,
    val robot: Robot? = null,
    val trayectoria: List<Pair<Int, Int>> = emptyList()
)