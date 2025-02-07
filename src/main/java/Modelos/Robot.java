package Modelos;

import cu.edu.cujae.ceis.graph.vertex.Vertex;
import cu.edu.cujae.ceis.graph.vertex.WeightedVertex;

import java.util.LinkedList;

public class Robot {
    private Vertex posicionActual;
    private final Tablero tablero;
    private final Vertex meta;
    private final GestorTrayectoria gestorTrayectoria;

    public Robot(Tablero tablero, Vertex meta) {
        this.tablero = tablero;
        this.meta = meta;
        this.posicionActual = tablero.obtenerCasillaAleatoria();
        this.gestorTrayectoria = new GestorTrayectoria();
        this.gestorTrayectoria.agregarPunto(posicionActual); // Agregar la posición inicial

    }

    public void mover() {
        int steps = 0;
        int maxSteps = (tablero.getFilas() * tablero.getColumnas()) / 2;

        while (steps < maxSteps && !posicionActual.equals(meta)) {
            Vertex siguiente = encontrarMejorMovimiento();
            if (siguiente.equals(posicionActual)) {
                posicionActual = siguiente;
                gestorTrayectoria.agregarPunto(posicionActual); // Agregar la nueva posición
                steps++;
            }
        }

        if (posicionActual.equals(meta)) {
            System.out.println("El robot alcanzó la meta en " + steps + " pasos.");
        } else {
            System.out.println("El robot no pudo alcanzar la meta en el límite de pasos.");
        }

        // Mostrar la trayectoria
        gestorTrayectoria.mostrarTrayectoria();
    }

    private Vertex encontrarMejorMovimiento() {
        Vertex mejor = posicionActual;
        int minPeso = (Integer) ((WeightedVertex) posicionActual).getWeight();

        LinkedList<Vertex> adyacentes = tablero.getGrafo().adjacentsG(tablero.getGrafo().getVerticesList().indexOf(posicionActual));
        for (Vertex vecino : adyacentes) {
            if (((Casilla) vecino.getInfo()).isActiva()) {
                int pesoVecino = (Integer) ((WeightedVertex) vecino).getWeight();
                if (pesoVecino < minPeso) {
                    mejor = vecino;
                    minPeso = pesoVecino;
                }
            }

        }
        return mejor;
    }

    public void setPosicionActual(Vertex posicionActual) {
        if (posicionActual != null) {
            this.posicionActual = posicionActual;
        }
    }

    public Meta obtenerMeta() {
        return (Meta) this.meta.getInfo();
    }

    public Casilla getPosicionActual() {
        return (Casilla) posicionActual.getInfo();
    }

    public GestorTrayectoria getGestorTrayectoria() {
        return gestorTrayectoria;
    }
}


