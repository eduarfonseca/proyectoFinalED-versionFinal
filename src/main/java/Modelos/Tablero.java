package Modelos;

import java.util.Random;

public class Tablero {
    private final boolean[][] tablero; // true: activa, false: inactiva
    private final int filas;
    private final int columnas;

    /**
     * Constructor para inicializar el tablero con dimensiones dadas.
     *
     * @param filas    Número de filas del tablero.
     * @param columnas Número de columnas del tablero.
     */
    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.tablero = new boolean[filas][columnas];

        // Inicializamos todas las casillas como activas por defecto
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = true;
            }
        }
    }

    /**
     * Genera casillas inactivas en el tablero aleatoriamente.
     *
     * @param porcentajeInactivas Porcentaje de casillas que serán inactivas.
     *                            Ejemplo: 30 significa que el 30% de las
     *                            casillas serán inactivas.
     */
    public void inicializarCasillasInactivas(int porcentajeInactivas) {
        if (porcentajeInactivas < 0 || porcentajeInactivas > 100) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100.");
        }

        int totalCasillas = filas * columnas;
        int cantidadInactivas = (int) Math.ceil((porcentajeInactivas / 100.0) * totalCasillas);
        Random random = new Random();

        int inactivasColocadas = 0;
        while (inactivasColocadas < cantidadInactivas) {
            int filaAleatoria = random.nextInt(filas);
            int columnaAleatoria = random.nextInt(columnas);

            // Si la casilla ya está inactiva, se ignora
            if (tablero[filaAleatoria][columnaAleatoria]) {
                tablero[filaAleatoria][columnaAleatoria] = false;
                inactivasColocadas++;
            }
        }
    }

    /**
     * Devuelve el estado del tablero.
     *
     * @return Matriz de booleanos que representa el tablero.
     */
    public boolean[][] getTablero() {
        return tablero;
    }

    /**
     * Muestra el tablero en consola, para depuración.
     * Las casillas activas se muestran como 'A' y las inactivas como 'I'.
     */
    public void mostrarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(tablero[i][j] ? "A " : "I ");
            }
            System.out.println();
        }
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }
}
