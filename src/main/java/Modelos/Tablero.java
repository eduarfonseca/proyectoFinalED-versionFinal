package Modelos;

import Interfaces.GeneradorTablero;
import Interfaces.GestionadorMeta;
import Interfaces.GestionadorTablero;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tablero implements GeneradorTablero, GestionadorMeta, GestionadorTablero {
    private final boolean[][] tablero; // true: activa, false: inactiva
    private final int filas;
    private final int columnas;
    private final Random random;
    private int[] meta; // Coordenadas de la meta [fila, columna]

    public Tablero(int filas, int columnas, long seed) {
        this.filas = filas;
        this.columnas = columnas;
        this.tablero = new boolean[filas][columnas];
        this.random = new Random(seed);

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = true;
            }
        }

        inicializarMeta();
    }

    // Implementación de TableroGenerador
    @Override
    public void inicializarCasillasInactivas(int porcentajeInactivas) {
        if (porcentajeInactivas < 0 || porcentajeInactivas > 100) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100.");
        }

        int totalCasillas = filas * columnas;
        int cantidadInactivas = (int) Math.ceil((porcentajeInactivas / 100.0) * totalCasillas);

        int inactivasColocadas = 0;

        while (inactivasColocadas < cantidadInactivas) {
            int filaAleatoria = random.nextInt(filas);
            int columnaAleatoria = random.nextInt(columnas);

            if (tablero[filaAleatoria][columnaAleatoria]) {
                tablero[filaAleatoria][columnaAleatoria] = false;
                inactivasColocadas++;
            }
        }

        generarRutaActiva();
        ajustarMeta();
    }

    @Override
    public void mostrarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (meta[0] == i && meta[1] == j) {
                    System.out.print("M ");
                } else {
                    System.out.print(tablero[i][j] ? "A " : "I ");
                }
            }
            System.out.println();
        }
    }

    @Override
    public boolean[][] getTablero() {
        return tablero;
    }

    // Implementación de MetaManager
    @Override
    public int[] getMeta() {
        return meta;
    }

    @Override
    public void setMeta(int[] meta) {
        this.meta = meta;
    }

    @Override
    public void ajustarMeta() {
        if (!tablero[meta[0]][meta[1]]) {
            inicializarMeta();
        }
    }

    private void inicializarMeta() {
        int fila, columna;
        do {
            fila = random.nextInt(filas);
            columna = random.nextInt(columnas);
        } while (!tablero[fila][columna]);

        meta = new int[]{fila, columna};
    }

    // Implementación de TableroEstado
    @Override
    public List<Pair<Integer, Integer>> obtenerCasillasActivas() {
        List<Pair<Integer, Integer>> casillasActivas = new ArrayList<>();

        for (int fila = 0; fila < tablero.length; fila++) {
            for (int columna = 0; columna < tablero[fila].length; columna++) {
                if (tablero[fila][columna]) {
                    casillasActivas.add(new Pair<>(fila, columna));
                }
            }
        }

        return casillasActivas;
    }

    @Override
    public Pair<Integer, Integer> obtenerMeta() {
        return new Pair<>(meta[0], meta[1]);
    }

    @Override
    public int getFilas() {
        return filas;
    }

    @Override
    public int getColumnas() {
        return columnas;
    }

    // Método auxiliar privado
    private void generarRutaActiva() {
        int fila = 0;
        int columna = 0;

        tablero[fila][columna] = true;

        while (fila < filas - 1 || columna < columnas - 1) {
            if (fila < filas - 1 && (columna == columnas - 1 || random.nextBoolean())) {
                fila++;
            } else {
                columna++;
            }
            tablero[fila][columna] = true;
        }
    }
}


