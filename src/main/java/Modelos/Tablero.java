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
        this.grafo = new LinkedGraph(); // Asume que LinkedGraph implementa ILinkedWeightedVertexNotDirectedGraph
        inicializarTablero();
    }

    private void inicializarTablero() {
        // Crear vértices para cada casilla
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Casilla casilla = new Casilla(i, j, true); // Todas las casillas activas por defecto
                grafo.insertWVertex(casilla, 0); // Peso inicial 0
            }
        }

        // Conectar casillas adyacentes (aristas no tienen peso)
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

        desactivarCasillasAleatoriamente(porcentajeInactivas);

        // Reconstruir el grafo con nuevas distancias
        List<Vertex> verticesViejos = new ArrayList<>(grafo.getVerticesList());
        grafo.getVerticesList().clear();

        for (Vertex viejo : verticesViejos) {
            Casilla casilla = (Casilla) viejo.getInfo();
            if (casilla.isActiva()){
                int distancia = (casilla.equals(meta)) ? 0 : distanciaManhattan(casilla, meta);
                grafo.insertWVertex(casilla, distancia); // Nuevo WeightedVertex
            }
        }

        // Reconectar todas las aristas según coordenadas
        for (int i = 0; i < verticesViejos.size(); i++) {
            Casilla casilla = (Casilla) verticesViejos.get(i).getInfo();
            int x = casilla.getX();
            int y = casilla.getY();

            if (x > 0) reconectar(x, y, x - 1, y);
            if (x < filas - 1) reconectar(x, y, x + 1, y);
            if (y > 0) reconectar(x, y, x, y - 1);
            if (y < columnas - 1) reconectar(x, y, x, y + 1);
        }
    }

    private void reconectar(int x1, int y1, int x2, int y2) {
        int idx1 = encontrarPosicionPorCasilla(x1, y1);
        int idx2 = encontrarPosicionPorCasilla(x2, y2);

        if (idx1 != -1 && idx2 != -1) {
            grafo.insertEdgeNDG(idx1, idx2);
        }
    }

    private int encontrarPosicionPorCasilla(int x, int y) {
        for (int i = 0; i < grafo.getVerticesList().size(); i++) {
            Casilla c = (Casilla) grafo.getVerticesList().get(i).getInfo();
            if (c.getX() == x && c.getY() == y) return i;
        }
        return -1;
    }

    private int distanciaManhattan(Casilla a, Casilla b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    public ILinkedWeightedVertexNotDirectedGraph getGrafo() {
        return grafo;
    }

    public boolean coordenadasValidas(int x, int y) {
        return buscarWVertexCoordenadas(x, y) != null;
    }
    public WeightedVertex buscarWVertexCoordenadas(int x, int y) {
        for (Vertex v : grafo.getVerticesList()) {
            Casilla casilla = (Casilla) v.getInfo();
            if (casilla.getX() == x && casilla.getY() == y) {
                return (WeightedVertex) v;
            }
        }
        return null;
    }
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

    public boolean esCasillaActivada(int x, int y) {
        boolean result = false;
        WeightedVertex v = buscarWVertexCoordenadas(x, y);
        if (v != null) {
            result = ((Casilla)v.getInfo()).isActiva();

        }
        return result;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
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

    public List<Pair<Integer, Integer>> obtenerParesCasillasActiv() {
        List<Pair<Integer, Integer>> pares = new LinkedList<>();
        Iterator<Casilla> itActivas = this.obtenerCasillasActivas().iterator();
        while (itActivas.hasNext()) {
            Casilla casilla = itActivas.next();
            pares.add(new Pair<>(casilla.getX(), casilla.getY()));
        }
        return pares;
    }

    public void setColumnas(int columnas) {
        if (columnas > 0)
            this.columnas = columnas;
    }

    public void setFilas(int filas) {
        if (filas > 0)
            this.filas = filas;
    }

    public Vertex obtenerCasillaAleatoria() {
        LinkedList<Vertex> vertices = grafo.getVerticesList();
        return vertices.get((int) (Math.random() * vertices.size()));
    }

    public void desactivarCasillasAleatoriamente(int porcentaje) {
        List<Casilla> candidatas = new ArrayList<>();
        Iterator<Vertex> it = grafo.getVerticesList().iterator();
        while (it.hasNext()) {
            WeightedVertex v = (WeightedVertex) it.next();
            if ((Integer) v.getWeight() != 0) {
                candidatas.add((Casilla) v.getInfo());
            }
        }
        // 2. Calcular número de casillas a desactivar
        int totalDesactivar = (candidatas.size() * porcentaje) / 100;
        // 3. Aleatorizar y desactivar
        Collections.shuffle(candidatas);
        for (int i = 0; i < totalDesactivar; i++) {
            candidatas.get(i).setActiva(false);
        }
    }
}