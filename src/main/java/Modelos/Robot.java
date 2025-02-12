package Modelos;

import cu.edu.cujae.ceis.graph.vertex.Vertex;
import cu.edu.cujae.ceis.graph.vertex.WeightedVertex;

import java.util.LinkedList;

public class Robot {
    private WeightedVertex posicionActual;
    private final Tablero tablero;
    private WeightedVertex meta;
    private final GestorTrayectoria gestorTrayectoria;
    private int pasosDados;

    public Robot(Tablero tablero, WeightedVertex meta) {
        this.tablero = tablero;
        this.meta = meta;
        this.posicionActual = (WeightedVertex) tablero.obtenerCasillaAleatoria();
        this.gestorTrayectoria = new GestorTrayectoria();
        this.gestorTrayectoria.agregarPunto(posicionActual);
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
            gestorTrayectoria.agregarPunto(posicionActual);
            this.pasosDados++;
        }

        // Mostrar resultados
        System.out.println("\n--- Resumen de la Simulación ---");
        if ((posicionActual.getInfo()).equals(casillaMeta)) {
            System.out.println("¡Meta alcanzada en " + this.pasosDados + " pasos!");
        } else {
            System.out.println("Límite de pasos alcanzado (" + maxPasos + ")");
        }
        gestorTrayectoria.mostrarTrayectoria();
    }
//    public void mover() {
//        int maxSteps = (tablero.getFilas() * tablero.getColumnas()) / 2;
//
//        while (pasosDados < maxSteps && !haLlegadoAMeta()) {
//            Vertex mejorMovimiento = encontrarMejorMovimiento();
//
//            // Si no hay mejor movimiento disponible y no estamos en la meta, contar como paso perdido
//            if (mejorMovimiento == null) {
//                pasosDados++;
//                continue;
//            }
//
//            // Si el mejor movimiento tiene un peso mayor o igual, es un paso perdido
//            int pesoActual = (Integer) ((WeightedVertex) posicionActual).getWeight();
//            int pesoMejor = (Integer) ((WeightedVertex) mejorMovimiento).getWeight();
//
//            if (pesoMejor >= pesoActual) {
//                pasosDados++;
//                continue;
//            }
//
//            // Realizar el movimiento
//            posicionActual = mejorMovimiento;
//            gestorTrayectoria.agregarPunto(posicionActual);
//            pasosDados++;
//        }
//
//        if (haLlegadoAMeta()) {
//            System.out.println("¡El robot alcanzó la meta en " + pasosDados + " pasos!");
//        } else {
//            System.out.println("El robot no pudo alcanzar la meta en el límite de " + maxSteps + " pasos.");
//        }
//    }

//    private Vertex encontrarMejorMovimiento() {
//        Vertex mejorMovimiento = null;
//        int pesoActual = (Integer) ((WeightedVertex) posicionActual).getWeight();
//        int mejorPeso = Integer.MAX_VALUE;
//
//        LinkedList<Vertex> adyacentes = tablero.getGrafo().adjacentsG(
//                tablero.getGrafo().getVerticesList().indexOf(posicionActual)
//        );
//
//        for (Vertex vecino : adyacentes) {
//            Casilla casillaVecina = (Casilla) vecino.getInfo();
//
//            // Verificar si la casilla está activa
//            if (!casillaVecina.isActiva()) {
//                continue;
//            }
//
//            int pesoVecino = (Integer) ((WeightedVertex) vecino).getWeight();
//
//            // Actualizar mejor movimiento si encontramos uno con menor peso
//            if (pesoVecino < mejorPeso) {
//                mejorPeso = pesoVecino;
//                mejorMovimiento = vecino;
//            }
//        }
//
//        return mejorMovimiento;
//    }

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
        this.posicionActual = posicionActual;
    }

    public void setMeta(WeightedVertex meta) {
        this.meta = meta;
    }

    public GestorTrayectoria getGestorTrayectoria() {
        return gestorTrayectoria;
    }
}


