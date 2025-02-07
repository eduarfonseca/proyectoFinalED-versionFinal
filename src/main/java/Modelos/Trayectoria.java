package Modelos;

import cu.edu.cujae.ceis.graph.vertex.Vertex;

import java.util.LinkedList;

public class Trayectoria {
    private final LinkedList<Vertex> casillas; // Lista de casillas en la trayectoria

    public Trayectoria() {
        this.casillas = new LinkedList<>();
    }

    public void agregarCasilla(Vertex casilla) {
        this.casillas.addLast(casilla);
    }

    public LinkedList<Vertex> getCasillas() {
        return casillas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Trayectoria:\n");
        for (Vertex casilla : casillas) {
            sb.append(casilla).append("\n");
        }
        return sb.toString();
    }

}