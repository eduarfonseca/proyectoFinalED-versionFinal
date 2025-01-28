package Interfaces;

public interface GeneradorTablero {
    void inicializarCasillasInactivas(int porcentajeInactivas);
    void mostrarTablero();
    boolean[][] getTablero();
}
