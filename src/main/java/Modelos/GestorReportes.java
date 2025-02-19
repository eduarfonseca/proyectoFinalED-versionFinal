package Modelos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class GestorReportes {
    Robot robot;
    File ficheroTrayectoria;

    public GestorReportes(Robot robot) {
        this.robot = robot;
        ficheroTrayectoria = new File("reporte.DAT", "rw");
    }

    public void crearReporteTrayectoria() throws IOException {
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
    }
}
