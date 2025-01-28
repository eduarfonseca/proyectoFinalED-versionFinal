package Modelos;

import Interfaces.GestionTrayectoria;
import Interfaces.MovimientoRobot;
import Interfaces.ValidacionRobot;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;

public class Robot implements MovimientoRobot, ValidacionRobot, GestionTrayectoria {
    private int filaActual;
    private int columnaActual;
    private final int pasosMaximos;

    public Robot(int filaInicial, int columnaInicial, int pasosMaximos) {
        this.filaActual = filaInicial;
        this.columnaActual = columnaInicial;
        this.pasosMaximos = pasosMaximos;
    }

    // Implementación de MovimientoRobot
    @Override
    public List<Paso> moverHaciaMeta(boolean[][] tablero, int metaFila, int metaColumna) {
        List<Paso> pasos = new ArrayList<>();
        int pasosDados = 0;

        if (!tablero[filaActual][columnaActual]) {
            pasos.add(new Paso(pasosDados, filaActual, columnaActual, "¡Perdió! Inicio en casilla inactiva."));
            return pasos;
        }

        pasos.add(new Paso(pasosDados, filaActual, columnaActual, "Inicio"));

        while (!(filaActual == metaFila && columnaActual == metaColumna) && pasosDados < pasosMaximos) {
            List<int[]> movimientosValidos = new ArrayList<>();
            int mejorDistancia = calcularDistanciaManhattan(filaActual, columnaActual, metaFila, metaColumna);

            for (int[] movimiento : obtenerMovimientosPosibles()) {
                int nuevaFila = filaActual + movimiento[0];
                int nuevaColumna = columnaActual + movimiento[1];

                if (esMovimientoValido(tablero, nuevaFila, nuevaColumna)) {
                    int nuevaDistancia = calcularDistanciaManhattan(nuevaFila, nuevaColumna, metaFila, metaColumna);

                    if (nuevaDistancia <= mejorDistancia) {
                        mejorDistancia = nuevaDistancia;
                        movimientosValidos.add(new int[]{nuevaFila, nuevaColumna});
                    }
                }
            }

            pasosDados++;

            if (movimientosValidos.isEmpty()) {
                pasos.add(new Paso(pasosDados, filaActual, columnaActual, "Paso perdido. No hay movimientos válidos."));
                continue;
            }

            int[] mejorMovimiento = movimientosValidos.get(0);
            filaActual = mejorMovimiento[0];
            columnaActual = mejorMovimiento[1];

            if (!tablero[filaActual][columnaActual]) {
                pasos.add(new Paso(pasosDados, filaActual, columnaActual, "¡Perdió! Casilla inactiva."));
                return pasos;
            }

            pasos.add(new Paso(pasosDados, filaActual, columnaActual, "Movimiento realizado."));
        }

        if (filaActual == metaFila && columnaActual == metaColumna) {
            pasos.add(new Paso(pasosDados, filaActual, columnaActual, "Meta alcanzada."));
        } else {
            pasos.add(new Paso(pasosDados, filaActual, columnaActual, "No se pudo llegar a la meta. Pasos máximos alcanzados."));
        }

        return pasos;
    }

    @Override
    public List<int[]> obtenerMovimientosPosibles() {
        return List.of(
                new int[]{-1, 0}, // Arriba
                new int[]{1, 0},  // Abajo
                new int[]{0, -1}, // Izquierda
                new int[]{0, 1}   // Derecha
        );
    }

    // Implementación de ValidacionRobot
    @Override
    public int calcularDistanciaManhattan(int fila1, int columna1, int fila2, int columna2) {
        return Math.abs(fila1 - fila2) + Math.abs(columna1 - columna2);
    }

    @Override
    public boolean esMovimientoValido(boolean[][] tablero, int nuevaFila, int nuevaColumna) {
        return nuevaFila >= 0 && nuevaFila < tablero.length &&
                nuevaColumna >= 0 && nuevaColumna < tablero[0].length &&
                tablero[nuevaFila][nuevaColumna];
    }

    // Implementación de GestionTrayectoria
    @Override
    public List<Pair<Integer, Integer>> obtenerTrayectoria(List<Paso> pasos) {
        List<Pair<Integer, Integer>> trayectoria = new ArrayList<>();

        for (Paso paso : pasos) {
            trayectoria.add(new Pair<>(paso.getFila(), paso.getColumna()));
        }

        return trayectoria;
    }

    public int getPasosMaximos() {
        return pasosMaximos;
    }
}




