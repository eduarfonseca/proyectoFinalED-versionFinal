package Navegacion

sealed class Screen(val route: String) {
    object Inicio : Screen("inicio")
    object Tablero : Screen("tablero")
    object Reportes : Screen("reportes")

    companion object {
        fun fromRoute(route: String?): Screen {
            return when(route) {
                Inicio.route -> Inicio
                Tablero.route -> Tablero
                Reportes.route -> Reportes
                else -> Inicio
            }
        }
    }
}
