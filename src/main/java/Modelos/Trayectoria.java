package Modelos;

import cu.edu.cujae.ceis.graph.vertex.Vertex;
import cu.edu.cujae.ceis.graph.vertex.WeightedVertex;
import kotlin.Pair;

import java.util.*;

public class Trayectoria {
    private WeightedVertex casilla;
    private String direccion;
    private int distancia;

    public WeightedVertex getCasilla() {
        return casilla;
    }

    public void setCasilla(WeightedVertex casilla) {
        this.casilla = casilla;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Trayectoria(WeightedVertex casilla, String direccion) {
        this.casilla = casilla;
        this.direccion = direccion;
        this.distancia = ((Heuristica)casilla.getWeight()).getDistancia();
    }
    public int getDistancia() {
        return distancia;
    }
}