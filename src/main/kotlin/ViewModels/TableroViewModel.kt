package ViewModels

import Modelos.Casilla
import Modelos.Robot
import Modelos.Tablero
import Modelos.TableroState
import Modelos.CasillaSeleccionada
import androidx.lifecycle.ViewModel
import cu.edu.cujae.ceis.graph.vertex.WeightedVertex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class TableroViewModel : ViewModel() {
    private val _state = MutableStateFlow(TableroState())
    val state: StateFlow<TableroState> = _state.asStateFlow()

    init {
        val tableroInicial = Tablero(10, 10)
        _state.value = _state.value.copy(
            tablero = tableroInicial,
            casillasActivas = tableroInicial.obtenerParesCasillasActiv(),
            meta = tableroInicial.grafo.verticesList[0] as WeightedVertex,
            inicio = tableroInicial.grafo.verticesList[1] as WeightedVertex
        )
        actualizarRobot()
    }

    fun setModoSeleccion(modo: String?) {
        _state.value = _state.value.copy(
            modoSeleccion = if (_state.value.modoSeleccion == modo) null else modo
        )
    }

    fun actualizarDimensionesTablero(filas: Int, columnas: Int) {
        val nuevoTablero = Tablero(filas, columnas)
        _state.value = _state.value.copy(
            filas = filas,
            columnas = columnas,
            tablero = nuevoTablero,
            casillasActivas = nuevoTablero.obtenerParesCasillasActiv()
        )
        actualizarPesosTablero()
    }

    fun actualizarPesosTablero() {
        _state.value.tablero?.let { tablero ->
            _state.value.meta?.let { meta ->
                tablero.actualizarPesos(meta.info as Casilla, _state.value.selectedNumber)
                _state.value = _state.value.copy(
                    casillasActivas = tablero.obtenerParesCasillasActiv()
                )
            }
        }
    }

    fun setSelectedNumber(number: Int) {
        _state.value = _state.value.copy(selectedNumber = number)
    }

    fun desactivarCasillasAleatorias() {
        _state.value.tablero?.let { tablero ->
            tablero.desactivarCasillasAleatoriamente(_state.value.selectedNumber)
            _state.value = _state.value.copy(
                casillasActivas = tablero.obtenerParesCasillasActiv()
            )
        }
    }

    fun seleccionarCasilla(casillaSeleccionada: CasillaSeleccionada) {
        when (_state.value.modoSeleccion) {
            "meta" -> {
                _state.value = _state.value.copy(
                    casillaMeta = casillaSeleccionada,
                    modoSeleccion = null,
                    meta = _state.value.tablero?.grafo?.verticesList?.get(casillaSeleccionada.numero) as? WeightedVertex
                )
                actualizarRobot()
            }
            "inicio" -> {
                _state.value = _state.value.copy(
                    casillaInicio = casillaSeleccionada,
                    modoSeleccion = null,
                    inicio = _state.value.tablero?.grafo?.verticesList?.get(casillaSeleccionada.numero) as? WeightedVertex
                )
                actualizarRobot()
            }
        }
    }

    fun iniciarSimulacion() {

        actualizarRobot()
        // Aquí puedes añadir la lógica adicional para la simulación
    }

    private fun actualizarRobot() {
        _state.value.tablero?.let { tablero ->
            _state.value.meta?.let { meta ->
                _state.value.inicio?.let { inicio ->
                    val nuevoRobot = Robot(tablero, meta, inicio)
                    _state.value = _state.value.copy(
                        robot = nuevoRobot,
                        trayectoria = nuevoRobot.gestorTrayectoria.obtenerPairsTrayectoria()
                    )
                }
            }
        }
    }
}
