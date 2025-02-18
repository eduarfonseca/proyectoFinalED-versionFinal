package Modelos;

import cu.edu.cujae.ceis.graph.vertex.Vertex;
import cu.edu.cujae.ceis.graph.vertex.WeightedVertex;

import java.util.Iterator;
import java.util.LinkedList;

public class Robot {
    private WeightedVertex posicionActual;
    private final Tablero tablero;
    private WeightedVertex meta;
    private final GestorTrayectoria gestorTrayectoria;
    private int pasosDados;

    public Robot(Tablero tablero, WeightedVertex meta, WeightedVertex posicionActual) {
        this.tablero = tablero;
        setMeta(meta);
        setPosicionActual(posicionActual);
        this.gestorTrayectoria = new GestorTrayectoria();
        this.pasosDados = 0;
    }


    public Casilla obtenerMeta() {
        return (Casilla) this.meta.getInfo();
    }

    public Casilla getPosicionActual() {
        return (Casilla) posicionActual.getInfo();
    }

    public WeightedVertex obtenerPosicionActual() {
        return posicionActual;
    }

    public void setPosicionActual(WeightedVertex posicionActual) {
        if (((Casilla) posicionActual.getInfo()).isActiva())
            this.posicionActual = posicionActual;
        else throw new IllegalArgumentException("La posicion inicial  debe ser activa");
    }

    public void setMeta(WeightedVertex meta) {
        if (((Casilla) meta.getInfo()).isActiva())
            this.meta = meta;
        else throw new IllegalArgumentException("La meta debe ser activa");
    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////
    public int calcularMaxPasos() {
        return (tablero.getFilas() * tablero.getColumnas()) / 2;
    }


}


