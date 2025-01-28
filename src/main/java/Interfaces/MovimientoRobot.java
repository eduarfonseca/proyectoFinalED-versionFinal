package Interfaces;

import Modelos.Paso;

import java.util.List;

public interface MovimientoRobot {
    List<Paso> moverHaciaMeta(boolean[][] tablero, int metaFila, int metaColumna);
    List<int[]> obtenerMovimientosPosibles();
}
