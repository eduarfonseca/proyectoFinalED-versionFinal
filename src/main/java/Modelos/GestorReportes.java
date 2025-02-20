package Modelos;

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
    public static final String filename = "C:\\Users\\moles\\Documents\\Universidad\\Ficheros ED 2.0\\reporte2.csv";
    private File reporte2;

    public static GestorReportes getGestorReportes() {
        if(gestorReportes == null) {
            gestorReportes = new GestorReportes();
        }
        return gestorReportes;
    }
    private GestorReportes() {
        reporte2 = new File(filename);
        try {
            reporte2.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public File getReporte2() {
        return this.reporte2;
    }

    public LinkedList<Robot> metasAlcanzadas (LinkedList<Robot> robots){
        LinkedList<Robot> metas = new LinkedList<>();
        for(Robot robot : robots){
            int cantPasos = robot.getGestorTrayectoria().getTrayectoria().size();
            if (robot.obtenerMeta().equals(robot.getGestorTrayectoria().getTrayectoria().get(cantPasos - 1))) {
                metas.add(robot);
            }
        }
        Collections.sort(metas);
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
