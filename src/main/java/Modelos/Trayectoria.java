package Modelos;

import cu.edu.cujae.ceis.graph.vertex.Vertex;
import cu.edu.cujae.ceis.graph.vertex.WeightedVertex;

import java.util.LinkedList;

public class Trayectoria {
    private final LinkedList<WeightedVertex> casillas; // Lista de casillas en la trayectoria

    public Trayectoria() {
        this.casillas = new LinkedList<>();
    }

    public void agregarCasilla(WeightedVertex casilla) {
        this.casillas.addLast(casilla);
    }

    public LinkedList<WeightedVertex> getCasillas() {
        return casillas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int paso = 1;
        for (Vertex casilla : casillas) {
            Casilla c = (Casilla) casilla.getInfo();
            sb.append("Paso ").append(paso++).append(": (")
                    .append(c.getX()).append(", ").append(c.getY()).append(")\n");
        }
        return sb.toString();
    }

}