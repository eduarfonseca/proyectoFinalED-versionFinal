package Modelos;

import cu.edu.cujae.ceis.graph.vertex.WeightedVertex;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class GestorReportes {
    private static GestorReportes gestorReportes;
    public static final String report1 = "reporte1.dat";
    public static final String excel2 = "reporte2.csv";
    public static final String excel3 = "reporte3.csv";
    private final File reporte1;
    private final File reporte2;
    private final File reporte3;

    public static GestorReportes getGestorReportes() {
        if (gestorReportes == null) {
            gestorReportes = new GestorReportes();
        }
        return gestorReportes;
    }

    private GestorReportes() {
        reporte1 = new File(report1);
        reporte2 = new File(excel2);
        reporte3 = new File(excel3);
        try {
            reporte1.createNewFile();
            reporte2.createNewFile();
            reporte3.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getReporte2() {
        return this.reporte2;
    }

    public File getReporte3() {
        return this.reporte3;
    }

    public LinkedList<Robot> metasAlcanzadas(LinkedList<Robot> robots) {
        LinkedList<Robot> metas = new LinkedList<>();
        for (Robot robot : robots) {
            int distancia = robot.pesoDelUltimoPaso();
            System.out.println(distancia);
            if (distancia == 0) {
                metas.add(robot);
            }
        }
        System.out.println("la lista de listos en metas alcanzadas tiene este tamanno antes de ordenar -> " + metas.size());
        metas.sort(Comparator.comparingInt(r -> r.getGestorTrayectoria().getTrayectoria().size()));
        System.out.println("la lista de listos en metas alcanzadas tiene este tamanno despues de ordenar -> " + metas.size());
        return metas;
    }

    public void registrarMetasAlcanzadas(LinkedList<Robot> robots) throws IOException {
        System.out.println("la lista de robots al llamar a la funcion tiene tamanno -> " + robots.size());
        LinkedList<Robot> listos = metasAlcanzadas(robots);
        System.out.println("la lista de listos en metas alcanzadas tiene este tamanno -> " + listos.size());
        Iterator<Robot> it = listos.iterator();
        RandomAccessFile raf = new RandomAccessFile(reporte2, "rw");
        if (!listos.isEmpty()) {
            while (it.hasNext()) {
                Robot robot = it.next();
                // CASILLA DE INICIO
                byte[] casillaInicio = Convert.toBytes(robot.obtenerPosicionActual().getInfo());
                raf.writeInt(casillaInicio.length);
                raf.write(casillaInicio);
                //CASILLA META
                byte[] casillaMeta = Convert.toBytes(robot.obtenerMeta());
                raf.writeInt(casillaMeta.length);
                raf.write(casillaMeta);
                //Cantidad de Pasos
                raf.writeInt(robot.getGestorTrayectoria().getTrayectoria().size());
                // LA FECHA
                byte[] fecha = Convert.toBytes(robot.getGestorTrayectoria().getFecha());
                raf.writeInt(fecha.length);
                raf.write(fecha);
                raf.close();
            }
        } else
            throw new IllegalStateException("No hay datos para guardar");
    }

    public LinkedList<Robot> metasNoAlcanzadas(LinkedList<Robot> robots) {
        LinkedList<Robot> metasNoAlcanzadas = new LinkedList<>();
        for (Robot robot : robots) {
            int distancia = robot.pesoDelUltimoPaso();
            System.out.println(distancia);
            if (distancia != 0) {
                metasNoAlcanzadas.add(robot);
            }
        }
        metasNoAlcanzadas.sort(Comparator.comparingInt(r -> r.pesoDelUltimoPaso()));
        return metasNoAlcanzadas;
    }

    public void registrarMetasNoAlcanzadas(LinkedList<Robot> robots) throws IOException {
        LinkedList<Robot> listos = metasNoAlcanzadas(robots);
        Iterator<Robot> it = listos.iterator();
        RandomAccessFile raf = new RandomAccessFile(reporte3, "rw");
        if (!listos.isEmpty()) {
            while (it.hasNext()) {
                Robot robot = it.next();
                // CASILLA DE INICIO
                byte[] casillaInicio = Convert.toBytes((Casilla)robot.obtenerPosicionActual().getInfo());
                raf.writeInt(casillaInicio.length);
                raf.write(casillaInicio);
                //CASILLA META
                byte[] casillaMeta = Convert.toBytes(robot.obtenerMeta());
                raf.writeInt(casillaMeta.length);
                raf.write(casillaMeta);
                //DISTANCIA FALTANTE
                raf.writeInt(robot.pesoDelUltimoPaso());
                // LA FECHA
                byte[] fecha = Convert.toBytes(robot.getGestorTrayectoria().getFecha());
                raf.writeInt(fecha.length);
                raf.write(fecha);
                raf.close();
            }
        } else
            throw new IllegalStateException("No hay datos para guardar");

    }

    //Metodo ok
    public void crearReporteTrayectoria(LinkedList<Robot> robots) throws IOException {
        if (!robots.isEmpty()) {
            RandomAccessFile raf = new RandomAccessFile(reporte1, "rw");
            raf.seek(raf.length());

            Iterator<Robot> it = robots.iterator();

            while (it.hasNext()) {
                Robot robot = it.next();
                int sizeTrayectoria = robot.getGestorTrayectoria().getTrayectoria().size();
                raf.write(sizeTrayectoria);

                Iterator<Trayectoria> itRecorrido = robot.getGestorTrayectoria().getTrayectoria().iterator();

                while (itRecorrido.hasNext()) {
                    Trayectoria trayectoria = itRecorrido.next();
                    String dir = trayectoria.getDireccion();
                    byte[] arrayDir = Convert.toBytes(dir);
                    raf.write(arrayDir.length);
                    raf.write(arrayDir);
                }

                Casilla salida = (Casilla) robot.getGestorTrayectoria().getTrayectoria().getFirst().getCasilla().getInfo();
                Casilla meta = robot.obtenerMeta();
                byte[] arraySalida = Convert.toBytes(salida);
                byte[] arrayMeta = Convert.toBytes(meta);
                raf.write(arraySalida.length);
                raf.write(arraySalida);
                raf.write(arrayMeta.length);
                raf.write(arrayMeta);
            }

            raf.close();
        } else {
            throw new IllegalStateException("No hay datos para guardar");
        }
    }
}
