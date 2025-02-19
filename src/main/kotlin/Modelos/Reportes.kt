package Modelos

import java.util.LinkedList

data class Reportes (
    val listaRobots: LinkedList<Robot> = LinkedList(),
    val gestorReportes: GestorReportes = GestorReportes()
)