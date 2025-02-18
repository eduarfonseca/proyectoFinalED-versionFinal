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


    public Robot(Tablero tablero, WeightedVertex meta, WeightedVertex posicionActual) {
        this.tablero = tablero;
        setMeta(meta);
        setPosicionActual(posicionActual);
        this.gestorTrayectoria = new Trayectoria();
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

   public WeightedVertex mejorMovimiento(WeightedVertex actual) {
        WeightedVertex mejor = actual;
        int menor = ((Heuristica)mejor.getWeight()).getDistancia();
        Iterator<Vertex> iter = actual.getAdjacents().iterator();
        while (iter.hasNext()) {
            WeightedVertex wv = (WeightedVertex) iter.next();
            Casilla c = (Casilla) wv.getInfo();
            if(c.isActiva()){
                int dist = ((Heuristica)wv.getWeight()).getDistancia();
                if(dist < menor){
                    menor = dist;
                    mejor = wv ;
                }else {
                    if (dist == menor){
                        mejor = wv;
                    }
                }
            }
        }
        return mejor;
   }

   public void moverse (){
        int cantPasos = 0;
        int maxPasos = calcularMaxPasos();
        boolean find = true;
        WeightedVertex actual = this.posicionActual;
        while (cantPasos < maxPasos && !(actual.getInfo()).equals((Casilla) meta.getInfo()) && find){
            WeightedVertex aux = mejorMovimiento(actual);
            if(!aux.getInfo().equals(actual.getInfo())){
                String direccion = determinarDireccion((Casilla) aux.getInfo(),(Casilla) actual.getInfo());
                //  Crear la trayectoria con la direccion
                actual = aux;
                cantPasos++;
            }else {
                //No se encontro opcion igual ni mejor
                find = false;
            }
        }
   }

   public String determinarDireccion (Casilla nueva , Casilla vieja){
        String direccion = "";
        if(nueva.getX() < vieja.getX()){
            direccion = "Arriba";
        }else if(nueva.getX() > vieja.getX()){
            direccion = "Abajo";
        }else if(nueva.getY() > vieja.getY()){
            direccion = "Derecha";
        }else if(nueva.getY() < vieja.getY()){
            direccion = "Izquierda";
        }
        return direccion;
   }
}


