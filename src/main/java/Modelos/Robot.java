package Modelos;

import cu.edu.cujae.ceis.graph.vertex.Vertex;
import cu.edu.cujae.ceis.graph.vertex.WeightedVertex;
import java.util.LinkedList;

public class Robot {
    private Vertex posicionActual;
    private Tablero tablero;
    private Vertex meta;

    public Robot(Tablero tablero, Vertex meta) {
        this.tablero = tablero;
        this.meta = meta;
        this.posicionActual = obtenerCasillaAleatoria();
    }

    private Vertex obtenerCasillaAleatoria() {
        LinkedList<Vertex> vertices = tablero.getGrafo().getVerticesList();
        return vertices.get((int) (Math.random() * vertices.size()));
    }

    public void mover() {
        int steps = 0;
        int maxSteps = (tablero.getFilas() * tablero.getColumnas()) / 2;

        while (steps < maxSteps && !posicionActual.equals(meta)) {
            Vertex siguiente = encontrarMejorMovimiento();
            if (siguiente == null || siguiente.equals(posicionActual)) {
                break; // No hay mejora
            }
            posicionActual = siguiente;
            steps++;
        }

        if (posicionActual.equals(meta)) {
            System.out.println("El robot alcanzó la meta en " + steps + " pasos.");
        } else {
            System.out.println("El robot no pudo alcanzar la meta en el límite de pasos.");
        }
    }

    private Vertex encontrarMejorMovimiento() {
        Vertex mejor = posicionActual;
        int minPeso = (Integer) ((WeightedVertex) posicionActual).getWeight();

        LinkedList<Vertex> adyacentes = tablero.getGrafo().adjacentsG(tablero.getGrafo().getVerticesList().indexOf(posicionActual));
        for (Vertex vecino : adyacentes) {
            int pesoVecino = (Integer) ((WeightedVertex) vecino).getWeight();
            if (pesoVecino < minPeso) {
                mejor = vecino;
                minPeso = pesoVecino;
            }
        }

        return mejor;
    }
}


