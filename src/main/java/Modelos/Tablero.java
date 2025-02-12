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
        // Reconstruir el grafo con nuevas distancias
        List<Vertex> verticesViejos = new ArrayList<>(grafo.getVerticesList());
        grafo.getVerticesList().clear();

        for (Vertex viejo : verticesViejos) {
            Casilla casilla = (Casilla) viejo.getInfo();
            int distancia = (casilla.equals(meta)) ? 0 : distanciaManhattan(casilla, meta);
            grafo.insertWVertex(casilla, distancia); // Nuevo WeightedVertex
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

        desactivarCasillasAleatoriamente(porcentajeInactivas);
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

//    private void reemplazarVertice(WeightedVertex viejo, WeightedVertex nuevo) {
//        // 1. Obtener posición del vértice antiguo
//        int pos = grafo.getVerticesList().indexOf(viejo);
//        if (pos != -1) {
//            // 2. Eliminar el vértice antiguo
//            grafo.deleteVertex(pos);
//
//            // 3. Insertar nuevo vértice con la CASILLA como info
//            grafo.insertWVertex(nuevo.getInfo(), nuevo.getWeight()); // Corrección clave
//
//            // 4. Restaurar conexiones usando coordenadas
//            int nuevoIdx = grafo.getVerticesList().size() - 1;
//            for (Edge ady : viejo.getEdgeList()) {
//                int posAdy = encontrarPosicionPorCasilla((Casilla) ady.getVertex().getInfo());
//                if (posAdy != -1) {
//                    grafo.insertEdgeNDG(nuevoIdx, posAdy);
//                }
//            }
//        }
//    }

//    public Vertex obtenerVerticePorCasilla(Casilla casillaBuscada) {
//        for (Vertex v : grafo.getVerticesList()) {
//            Casilla c = (Casilla) v.getInfo();
//            if (c.equals(casillaBuscada)) {
//                return v;
//            }
//        }
//        throw new IllegalArgumentException("Casilla no encontrada en el grafo");
//    }

//    public void actualizarPesos(Casilla meta) {
//        // Crear una copia de la lista de vértices para evitar ConcurrentModificationException
//        LinkedList<Vertex> copiaVertices = new LinkedList<>(grafo.getVerticesList());
//        Iterator<Vertex> iterator = copiaVertices.iterator();
//
//        while (iterator.hasNext()) {
//            Vertex v = iterator.next();
//            Casilla casilla = (Casilla) v.getInfo(); // Obtener la Casilla del vértice
//            int distancia = distanciaManhattan(casilla, meta); // Calcular la distancia a la meta
//            // Crear un nuevo WeightedVertex con el peso actualizado
//            WeightedVertex nuevoVertice = new WeightedVertex(casilla, distancia);
//            // Reemplazar el vértice antiguo con el nuevo
//            reemplazarVertice((WeightedVertex) v, nuevoVertice);
//        }
//    }
//
//    private void reemplazarVertice(WeightedVertex viejo, WeightedVertex nuevo) {
//        // Obtener la posición del vértice antiguo
//        int pos = grafo.getVerticesList().indexOf(viejo);
//        if (pos != -1) {
//            // Eliminar el vértice antiguo
//            grafo.deleteVertex(pos);
//
//            // Insertar el nuevo vértice
//            grafo.insertWVertex(nuevo, nuevo.getWeight());
//
//            // Restaurar las conexiones (aristas) del vértice antiguo
//            LinkedList<Vertex> adyacentes = grafo.adjacentsG(pos);
//            for (Vertex adyacente : adyacentes) {
//                grafo.insertEdgeNDG(grafo.getVerticesList().indexOf(nuevo), grafo.getVerticesList().indexOf(adyacente));
//            }
//        }
//    }

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