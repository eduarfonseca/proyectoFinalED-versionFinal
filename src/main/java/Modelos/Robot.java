package Modelos;

import java.util.ArrayList;
import java.util.List;

public class Robot {
    private int filaActual;
    private int columnaActual;

    public Robot(int filaInicial, int columnaInicial) {
        this.filaActual = filaInicial;
        this.columnaActual = columnaInicial;
    }

    /**
     * Mueve el robot hacia la meta utilizando el algoritmo escalador de colinas.
     * Si el robot se mueve a una casilla inactiva, pierde.
     *
     * @param tablero     El tablero donde se mueve el robot.
     * @param metaFila    Fila de la meta.
     * @param metaColumna Columna de la meta.
     * @return Lista de pasos dados en el formato (fila, columna).
     */
    public List<String> moverHaciaMeta(boolean[][] tablero, int metaFila, int metaColumna) {
        List<String> pasos = new ArrayList<>();
        pasos.add("Inicio: (" + filaActual + ", " + columnaActual + ")");

        while (!(filaActual == metaFila && columnaActual == metaColumna)) {
            int mejorFila = filaActual;
            int mejorColumna = columnaActual;
            int mejorDistancia = calcularDistanciaManhattan(filaActual, columnaActual, metaFila, metaColumna);

            // Evaluar todos los posibles movimientos
            for (int[] movimiento : obtenerMovimientosPosibles()) {
                int nuevaFila = filaActual + movimiento[0];
                int nuevaColumna = columnaActual + movimiento[1];

                // Verificar si el movimiento es válido
                if (esMovimientoDentroDeLimites(tablero, nuevaFila, nuevaColumna)) {
                    int nuevaDistancia = calcularDistanciaManhattan(nuevaFila, nuevaColumna, metaFila, metaColumna);

                    // Actualizar si encontramos una casilla mejor
                    if (nuevaDistancia < mejorDistancia) {
                        mejorDistancia = nuevaDistancia;
                        mejorFila = nuevaFila;
                        mejorColumna = nuevaColumna;
                    }
                }
            }

            // Realizar el movimiento
            filaActual = mejorFila;
            columnaActual = mejorColumna;

            // Verificar si el robot se movió a una casilla inactiva
            if (!tablero[filaActual][columnaActual]) {
                pasos.add("¡Perdió! El robot se movió a una casilla inactiva: (" + filaActual + ", " + columnaActual + ")");
                return pasos;
            }

            pasos.add("Paso a: (" + filaActual + ", " + columnaActual + ")");
        }

        // Verificar si alcanzó la meta
        if (filaActual == metaFila && columnaActual == metaColumna) {
            pasos.add("Meta alcanzada en: (" + metaFila + ", " + metaColumna + ")");
        } else {
            pasos.add("No se pudo llegar a la meta.");
        }

        return pasos;
    }

    /**
     * Obtiene los posibles movimientos del robot.
     *
     * @return Una lista de movimientos en el formato (deltaFila, deltaColumna).
     */
    private List<int[]> obtenerMovimientosPosibles() {
        List<int[]> movimientos = new ArrayList<>();
        movimientos.add(new int[] { -1, 0 }); // Arriba
        movimientos.add(new int[] { 1, 0 });  // Abajo
        movimientos.add(new int[] { 0, -1 }); // Izquierda
        movimientos.add(new int[] { 0, 1 });  // Derecha
        return movimientos;
    }

    /**
     * Calcula la distancia Manhattan entre dos casillas.
     *
     * @param fila1    Fila de la primera casilla.
     * @param columna1 Columna de la primera casilla.
     * @param fila2    Fila de la segunda casilla.
     * @param columna2 Columna de la segunda casilla.
     * @return Distancia Manhattan entre las dos casillas.
     */
    private int calcularDistanciaManhattan(int fila1, int columna1, int fila2, int columna2) {
        return Math.abs(fila1 - fila2) + Math.abs(columna1 - columna2);
    }

    /**
     * Verifica si un movimiento está dentro de los límites del tablero.
     *
     * @param tablero    El tablero donde se mueve el robot.
     * @param nuevaFila    Nueva fila a evaluar.
     * @param nuevaColumna Nueva columna a evaluar.
     * @return true si el movimiento está dentro de los límites, false en caso contrario.
     */
    private boolean esMovimientoDentroDeLimites(boolean[][] tablero, int nuevaFila, int nuevaColumna) {
        return nuevaFila >= 0 && nuevaFila < tablero.length &&
                nuevaColumna >= 0 && nuevaColumna < tablero[0].length;
    }
}
