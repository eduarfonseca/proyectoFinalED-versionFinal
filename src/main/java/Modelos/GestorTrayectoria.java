package Modelos;

import cu.edu.cujae.ceis.graph.vertex.Vertex;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestorTrayectoria {
    private final Trayectoria trayectoria;

    public GestorTrayectoria() {
        this.trayectoria = new Trayectoria();
    }

    public void agregarPunto(Vertex casilla) {
        trayectoria.agregarCasilla(casilla);
    }

    public void mostrarTrayectoria() {
        System.out.println(trayectoria);
    }

    public Trayectoria getTrayectoria() {
        return trayectoria;
    }

    public List<Pair<Integer,Integer>> obtenerPairsTrayectoria() {
        List<Pair<Integer,Integer>> list = new ArrayList<>();
        Iterator<Vertex> casillas = trayectoria.getCasillas().iterator();
        while (casillas.hasNext()) {
            Vertex casilla = casillas.next();
            list.add(new Pair<>(((Casilla)casilla.getInfo()).getX(), ((Casilla)casilla.getInfo()).getY()));
        }
        return list;
    }
}