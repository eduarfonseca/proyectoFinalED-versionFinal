package Modelos;

import kotlin.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GestorTrayectoria {
    private final LinkedList<Trayectoria> trayectoria;

    public GestorTrayectoria() {
        this.trayectoria = new LinkedList<>();
    }

    public void agregarTrayectoria(Trayectoria t) {
        trayectoria.addLast(t);
    }

    public LinkedList<Trayectoria> getTrayectoria() {
        return trayectoria;
    }

    public List<Pair<Integer, Integer>> obtenerPairsTrayectoria() {
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        Iterator<Trayectoria> casillas = trayectoria.iterator();
        while (casillas.hasNext()) {
            Trayectoria t = casillas.next();
            list.add(new Pair<>(((Casilla) t.getCasilla().getInfo()).getX(), ((Casilla) t.getCasilla().getInfo()).getY()));
        }
        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int paso = 1;
        for (Trayectoria t : trayectoria) {
            Casilla c = (Casilla) t.getCasilla().getInfo();
            sb.append("Paso ").append(paso++).append(": (")
                    .append(c.getX()).append(", ").append(c.getY()).append(")").append("Direccion").append(t.getDireccion()).append("\n");
        }
        return sb.toString();
    }
}