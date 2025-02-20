package Modelos;

import cu.edu.cujae.ceis.graph.LinkedGraph;
import cu.edu.cujae.ceis.graph.edge.Edge;
import cu.edu.cujae.ceis.graph.vertex.Vertex;
import cu.edu.cujae.ceis.graph.vertex.WeightedVertex;
import cu.edu.cujae.ceis.graph.interfaces.ILinkedWeightedVertexNotDirectedGraph;
import kotlin.Pair;

import java.util.*;

public class Tablero {
    private final ILinkedWeightedVertexNotDirectedGraph grafo;
    private int filas;
    private int columnas;

    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.grafo = new LinkedGraph();
        inicializarTablero();
    }

    private void inicializarTablero() {
        // Crear vértices para cada casilla
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Casilla casilla = new Casilla(i, j, true);
                grafo.insertWVertex(casilla, new Heuristica(0)); // Peso inicial 0
            }
        }

        // Conectar casillas
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (i > 0) grafo.insertEdgeNDG(i * columnas + j, (i - 1) * columnas + j); // Arriba
                if (i < filas - 1) grafo.insertEdgeNDG(i * columnas + j, (i + 1) * columnas + j); // Abajo
                if (j > 0) grafo.insertEdgeNDG(i * columnas + j, i * columnas + (j - 1)); // Izquierda
                if (j < columnas - 1) grafo.insertEdgeNDG(i * columnas + j, i * columnas + (j + 1)); // Derecha
            }
        }

    }

    public void actualizarPesos(Casilla meta, int porcentajeInactivas) {

        //Asignar Distancias
        Iterator<Vertex> iter = grafo.getVerticesList().iterator();
        while (iter.hasNext()) {
            WeightedVertex vertex = (WeightedVertex) iter.next();
            Casilla c = (Casilla) vertex.getInfo();
            int distancia = (c.equals(meta)) ? 0 : distanciaManhattan(c, meta);
            ((Heuristica)vertex.getWeight()).modificarDistancia(distancia);
        }
        desactivarCasillasAleatoriamente(porcentajeInactivas);

        for (int i = 0 ;  i < grafo.getVerticesList().size() ; i ++ ){
            WeightedVertex vertex = (WeightedVertex) grafo.getVerticesList().get(i);
            Heuristica h = (Heuristica) vertex.getWeight();
            Casilla c = (Casilla) vertex.getInfo();
//            System.out.println("fila: " + c.getX() + "  columna: " + c.getY() + "  activo: " + c.isActiva() + "  peso " + h.getDistancia());
        }
    }

    private int distanciaManhattan(Casilla a, Casilla b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    public void desactivarCasillasAleatoriamente(int porcentaje) {
        List<Casilla> candidatas = new ArrayList<>();
        Iterator<Vertex> it = grafo.getVerticesList().iterator();
        while (it.hasNext()) {
            WeightedVertex v = (WeightedVertex) it.next();
            if (((Heuristica) v.getWeight()).getDistancia() != 0) {
                candidatas.add((Casilla) v.getInfo());
            }
        }
        int totalDesactivar = (candidatas.size() * porcentaje) / 100;
        Collections.shuffle(candidatas);
        for (int i = 0; i < totalDesactivar; i++) {
            candidatas.get(i).setActiva(false);
        }
    }

    public WeightedVertex buscarWVertexCoordenadas(int x, int y) {
        Iterator<Vertex> iter = grafo.getVerticesList().iterator();
        WeightedVertex v = null;
        while (iter.hasNext() && v == null) {
            WeightedVertex aux = (WeightedVertex) iter.next();
            Casilla c = (Casilla) aux.getInfo();
            if (c.getX() == x && c.getY() == y) {
                v = aux;
            }
        }
        return v;
    }

    public boolean esCasillaActivada(int x, int y) {
        WeightedVertex v = buscarWVertexCoordenadas(x, y);
        return v != null && ((Casilla) v.getInfo()).isActiva();
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        if (columnas > 0)
            this.columnas = columnas;
    }

    public void setFilas(int filas) {
        if (filas > 0)
            this.filas = filas;
    }
    public ILinkedWeightedVertexNotDirectedGraph getGrafo() {
        return grafo;
    }

    public Vertex obtenerCasillaAleatoria() {
        LinkedList<Vertex> vertices = grafo.getVerticesList();
        return vertices.get((int) (Math.random() * vertices.size()));
    }

    public LinkedList<Casilla> obtenerCasillasActivas() {
        LinkedList<Casilla> casillas = new LinkedList<>();
        Iterator<Vertex> it = grafo.getVerticesList().iterator();

        while (it.hasNext()) {
            Vertex v = it.next();
            Casilla casilla = (Casilla) v.getInfo();
            if (casilla.isActiva())
                casillas.addLast(casilla);
        }
        return casillas;
    }

    // Metodos para la vista
    public Tablero copy() {
        Tablero copia = new Tablero(this.filas, this.columnas);
        copia.grafo.getVerticesList().clear();

        // Copiar vértices
        for (Vertex v : this.grafo.getVerticesList()) {
            WeightedVertex wv = (WeightedVertex) v;
            Casilla original = (Casilla) wv.getInfo();
            Casilla copiaCasilla = new Casilla(original.getX(), original.getY(), original.isActiva());
            copia.grafo.insertWVertex(copiaCasilla, wv.getWeight());
        }

        // Copiar aristas
        for (int i = 0; i < this.grafo.getVerticesList().size(); i++) {
            Vertex original = this.grafo.getVerticesList().get(i);
            for (Edge e : original.getEdgeList()) {
                int destIdx = this.grafo.getVerticesList().indexOf(e.getVertex());
                copia.grafo.insertEdgeNDG(i, destIdx);
            }
        }

        return copia;
    }

    public List<Pair<Integer, Integer>> obtenerParesCasillasActiv() {
        List<Pair<Integer, Integer>> pares = new LinkedList<>();
        Iterator<Casilla> itActivas = this.obtenerCasillasActivas().iterator();
        while (itActivas.hasNext()) {
            Casilla casilla = itActivas.next();
            pares.add(new Pair<>(casilla.getX(), casilla.getY()));
        }
        return pares;
    }
}
