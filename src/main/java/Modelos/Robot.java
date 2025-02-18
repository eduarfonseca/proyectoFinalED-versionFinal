package Modelos;

import cu.edu.cujae.ceis.graph.vertex.Vertex;
import cu.edu.cujae.ceis.graph.vertex.WeightedVertex;

import java.util.Iterator;
import java.util.LinkedList;

public class Robot {
    private WeightedVertex posicionActual;
    private final Tablero tablero;
    private WeightedVertex meta;
    private final Trayectoria gestorTrayectoria;
    private int pasosDados;

    public Robot(Tablero tablero, WeightedVertex meta, WeightedVertex posicionActual) {
        this.tablero = tablero;
        setMeta(meta);
        setPosicionActual(posicionActual);
        this.gestorTrayectoria = new Trayectoria();
        this.pasosDados = 0;
    }

    private Vertex encontrarMejorMovimiento() {
        WeightedVertex mejor = posicionActual;
        int minDistancia = (int) mejor.getWeight();
        LinkedList<Vertex> opcionesValidas = new LinkedList<>();

        LinkedList<Vertex> adyacentes = tablero.getGrafo().adjacentsG(
                tablero.getGrafo().getVerticesList().indexOf(posicionActual)
        );

        for (Vertex vecino : adyacentes) {
            Casilla casillaVecina = (Casilla) vecino.getInfo();
            int distanciaVecino = (int) ((WeightedVertex) vecino).getWeight();

            if (casillaVecina.isActiva() && distanciaVecino <= minDistancia) {
                if (distanciaVecino < minDistancia) {
                    minDistancia = distanciaVecino;
                    opcionesValidas.clear();
                }
                opcionesValidas.add(vecino);
            }
        }

        return opcionesValidas.isEmpty() ? mejor :
                opcionesValidas.get((int)(Math.random() * opcionesValidas.size()));
    }
    public void mover() {
        int maxPasos = (tablero.getFilas() * tablero.getColumnas()) / 2;

        // Obtener la Casilla de la meta para comparaciones directas
        Casilla casillaMeta = (Casilla) meta.getInfo();

        System.out.println(casillaMeta.toString());

        // Condición del bucle basada en las Casillas (no en los Vertex)
        while (this.pasosDados < maxPasos && !(posicionActual.getInfo()).equals(casillaMeta)) {
            Vertex siguiente = encontrarMejorMovimiento();
            posicionActual = (WeightedVertex) siguiente;
            gestorTrayectoria.agregarCasilla(posicionActual);
            this.pasosDados++;
        }

        // Mostrar resultados
        System.out.println("\n--- Resumen de la Simulación ---");
        if ((posicionActual.getInfo()).equals(casillaMeta)) {
            System.out.println("¡Meta alcanzada en " + this.pasosDados + " pasos!");
        } else {
            System.out.println("Límite de pasos alcanzado (" + maxPasos + ")");
        }
        //Mostrar trayectoria por consola
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
        if (((Casilla)posicionActual.getInfo()).isActiva())
            this.posicionActual = posicionActual;
        else throw new IllegalArgumentException("La posicion inicial  debe ser activa");
    }

    public void setMeta(WeightedVertex meta) {
        if (((Casilla)meta.getInfo()).isActiva())
            this.meta = meta;
        else throw new IllegalArgumentException("La meta debe ser activa");
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////
   public int calcularMaxPasos(){
        return (tablero.getFilas() * tablero.getColumnas()) / 2;
   }




}


