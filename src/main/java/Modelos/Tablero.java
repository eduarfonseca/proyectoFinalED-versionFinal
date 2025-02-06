package Modelos;

import cu.edu.cujae.ceis.graph.LinkedGraph;
import cu.edu.cujae.ceis.graph.vertex.Vertex;
import cu.edu.cujae.ceis.graph.vertex.WeightedVertex;
import cu.edu.cujae.ceis.graph.interfaces.ILinkedWeightedVertexNotDirectedGraph;
import java.util.LinkedList;

public class Tablero {
    private ILinkedWeightedVertexNotDirectedGraph grafo;
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

    public void actualizarPesos(Casilla meta) {
        // Crear una copia de la lista de vértices para evitar ConcurrentModificationException
        LinkedList<Vertex> vertices = new LinkedList<>(grafo.getVerticesList());

        for (Vertex v : vertices) {
            Casilla casilla = (Casilla) v.getInfo(); // Obtener la Casilla del vértice
            int distancia = distanciaManhattan(casilla, meta); // Calcular la distancia a la meta

            // Crear un nuevo WeightedVertex con el peso actualizado
            WeightedVertex nuevoVertice = new WeightedVertex(casilla, distancia);

            // Reemplazar el vértice antiguo con el nuevo
            reemplazarVertice((WeightedVertex) v, nuevoVertice);
        }
    }

    private void reemplazarVertice(WeightedVertex viejo, WeightedVertex nuevo) {
        // Obtener la posición del vértice antiguo
        int pos = grafo.getVerticesList().indexOf(viejo);
        if (pos != -1) {
            // Eliminar el vértice antiguo
            grafo.deleteVertex(pos);

            // Insertar el nuevo vértice
            grafo.insertWVertex(nuevo, nuevo.getWeight());

            // Restaurar las conexiones (aristas) del vértice antiguo
            LinkedList<Vertex> adyacentes = grafo.adjacentsG(pos);
            for (Vertex adyacente : adyacentes) {
                grafo.insertEdgeNDG(grafo.getVerticesList().indexOf(nuevo), grafo.getVerticesList().indexOf(adyacente));
            }
        }
    }

    private int distanciaManhattan(Casilla a, Casilla b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    public ILinkedWeightedVertexNotDirectedGraph getGrafo() {
        return grafo;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }
}