package ViewModels

import Modelos.GestorReportes
import Modelos.Heuristica
import Modelos.Reportes
import Modelos.Robot
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class ReportesViewModel: ViewModel() {
    private val _state = MutableStateFlow(Reportes())
    val state: StateFlow<Reportes> = _state.asStateFlow()

    init {
        val listRobots = LinkedList<Robot>()
        val gestReportes = GestorReportes.getGestorReportes()
        _state.value = _state.value.copy(
            listaRobots = listRobots,
            gestorReportes = gestReportes
        )
    }

    fun addRobot(robot: Robot) {
        (robot.meta.weight as Heuristica).modificarDistancia(0)
        _state.value.listaRobots.addLast(robot)
    }

}