package Interfaces;


public interface ValidacionRobot {
    int calcularDistanciaManhattan(int fila1, int columna1, int fila2, int columna2);
    boolean esMovimientoValido(boolean[][] tablero, int nuevaFila, int nuevaColumna);
}
