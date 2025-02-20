package Modelos;

import cu.edu.cujae.ceis.graph.vertex.WeightedVertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.util.*;

public class GestorReportes {
    private static GestorReportes gestorReportes;
    public static final String excel2 = "C:\\Users\\moles\\Documents\\Universidad\\Ficheros ED 2.0\\reporte2.csv";
    public static final String excel3 = "C:\\Users\\moles\\Documents\\Universidad\\Ficheros ED 2.0\\reporte3.csv";
    private File reporte2;
    private File reporte3;

    public static GestorReportes getGestorReportes() {
        if(gestorReportes == null) {
            gestorReportes = new GestorReportes();
        }
        return gestorReportes;
    }
    private GestorReportes() {
        reporte2 = new File(excel2);
        reporte3 = new File(excel3);
        try {
            reporte2.createNewFile();
            reporte3.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public File getReporte2() {
        return this.reporte2;
    }

    public File getReporte3() {
        return this.reporte3;
    }

    public LinkedList<Robot> metasAlcanzadas (LinkedList<Robot> robots){
        LinkedList<Robot> metas = new LinkedList<>();
        for(Robot robot : robots){
            int cantPasos = robot.getGestorTrayectoria().getTrayectoria().size();
            if (robot.obtenerMeta().equals(robot.getGestorTrayectoria().getTrayectoria().get(cantPasos - 1))) {
                metas.add(robot);
            }
        }
        metas.sort(Comparator.comparingInt(r -> r.getGestorTrayectoria().getTrayectoria().size()));
        return metas;
    }

    public void registrarMetasAlcanzadas (LinkedList<Robot> robots) throws IOException {
        LinkedList<Robot> listos = metasAlcanzadas(robots);
        Iterator<Robot> it = listos.iterator();
        RandomAccessFile raf = new RandomAccessFile(reporte2, "rw");
        if(!listos.isEmpty()){
           while (it.hasNext()){
               Robot robot = it.next();
               // CASILLA DE INICIO
               byte[] casillaInicio = Convert.toBytes(robot.obtenerPosicionActual());
               raf.writeInt(casillaInicio.length);
               raf.write(casillaInicio);
               //CASILLA META
               byte[] casillaMeta = Convert.toBytes(robot.obtenerMeta());
               raf.writeInt(casillaMeta.length);
               raf.write(casillaMeta);
               //Cantidad de Pasos
               raf.writeInt(robot.getGestorTrayectoria().getTrayectoria().size());
               // FALTA LA FECHA
               raf.close();
           }
        }else
            throw new IllegalStateException("No hay datos para guardar");
    }

    public LinkedList<Robot> metasNoAlcanzadas (LinkedList<Robot> robots){
        LinkedList<Robot> metasNoAlcanzadas = new LinkedList<>();
        for(Robot robot : robots){
            int cantPasos = robot.getGestorTrayectoria().getTrayectoria().size();
            if (!robot.obtenerMeta().equals(robot.getGestorTrayectoria().getTrayectoria().get(cantPasos - 1))) {
                metasNoAlcanzadas.add(robot);
            }
        }
        metasNoAlcanzadas.sort(Comparator.comparingInt(r -> r.pesoDelUltimoPaso()));
        return metasNoAlcanzadas;
    }

    public void registrarMetasNoAlcanzadas (LinkedList<Robot> robots) throws IOException {
        LinkedList<Robot> listos = metasNoAlcanzadas(robots);
        Iterator<Robot> it = listos.iterator();
        RandomAccessFile raf = new RandomAccessFile(reporte3, "rw");
        if(!listos.isEmpty()){
            while (it.hasNext()){
                Robot robot = it.next();
                // CASILLA DE INICIO
                byte[] casillaInicio = Convert.toBytes(robot.obtenerPosicionActual());
                raf.writeInt(casillaInicio.length);
                raf.write(casillaInicio);
                //CASILLA META
                byte[] casillaMeta = Convert.toBytes(robot.obtenerMeta());
                raf.writeInt(casillaMeta.length);
                raf.write(casillaMeta);
                //DISTANCIA FALTANTE
                raf.writeInt(robot.pesoDelUltimoPaso());
                // FALTA LA FECHA
                raf.close();
            }
        }else
            throw new IllegalStateException("No hay datos para guardar");

    }



   /* public void crearReporteTrayectoria(Robot robot) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(ficheroTrayectoria, "rw");
        raf.seek(raf.length());
        int sizeTrayectoria = robot.getGestorTrayectoria().getTrayectoria().size();
        raf.write(sizeTrayectoria);

        for (int i = 0; i < sizeTrayectoria; i++) {
            String dir = robot.getGestorTrayectoria().getTrayectoria().get(i).getDireccion();
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
        raf.close();
    }*/
}
