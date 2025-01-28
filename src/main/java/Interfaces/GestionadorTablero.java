package Interfaces;

import kotlin.Pair;
import java.util.List;

public interface GestionadorTablero {
    List<Pair<Integer, Integer>> obtenerCasillasActivas();
    Pair<Integer, Integer> obtenerMeta();
    int getFilas();
    int getColumnas();
}
