package Modelos;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class GestorReportes {
    Robot robot;
    RandomAccessFile fichero;

    public GestorReportes(Robot robot) throws FileNotFoundException {
        this.robot = robot;
        fichero = new RandomAccessFile("reporte.dat", "rw");
    }


}
