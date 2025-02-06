package Modelos;

import cu.edu.cujae.ceis.graph.vertex.WeightedVertex;

public class Casilla {
    private int x;
    private int y;
    private boolean activa;

    public Casilla(int x, int y, boolean activa) {
        this.x = x;
        this.y = y;
        this.activa = activa;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    @Override
    public String toString() {
        return "Casilla(" + x + ", " + y + ", " + (activa ? "activa" : "inactiva") + ")";
    }
}