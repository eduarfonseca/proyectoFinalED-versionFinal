package Modelos;

import cu.edu.cujae.ceis.graph.vertex.WeightedVertex;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;


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
    //////////////////////////////////////////////////////////////////////////
    //Probando escribir con metodos csv
    public void csv1(LinkedList<Robot> robots) throws IOException {
       LinkedList<Robot> listos = metasAlcanzadas(robots);

        if (listos.isEmpty()) {
            throw new IllegalStateException("No hay datos para guardar");
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(this.excel2), ';', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            // Escribir la cabecera
            String[] encabezado = {"Casilla Inicio", "Casilla Meta", "Cantidad de Pasos", "Fecha"};
            writer.writeNext(encabezado);

            // Iterar y escribir datos de los robots
            for (Robot robot : listos) {
                String casillaInicio = robot.obtenerPosicionActual().getInfo().toString();
                String casillaMeta = robot.obtenerMeta().toString();
                String cantPasos = String.valueOf(robot.getGestorTrayectoria().getTrayectoria().size());
                String fecha = robot.getGestorTrayectoria().getFecha().toString();

                String[] datos = {casillaInicio, casillaMeta, cantPasos, fecha};
                writer.writeNext(datos);
            }

            System.out.println("CSV generado con éxito en: " + this.excel2);
        } catch (IOException e) {
            throw new IOException("Error al escribir el archivo CSV", e);
        }
    }

    public void csv2(LinkedList<Robot> robots) throws IOException {
        LinkedList<Robot> listos = metasNoAlcanzadas(robots);

        if (listos.isEmpty()) {
            throw new IllegalStateException("No hay datos para guardar");
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(this.excel3), ';', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
            // Escribir la cabecera
            String[] encabezado = {"Casilla Inicio", "Casilla Meta", "Distancia Faltante", "Fecha"};
            writer.writeNext(encabezado);

            // Iterar y escribir datos de los robots
            for (Robot robot : listos) {
                String casillaInicio = robot.obtenerPosicionActual().getInfo().toString();
                String casillaMeta = robot.obtenerMeta().toString();
                String distancia = String.valueOf(robot.pesoDelUltimoPaso());
                String fecha = robot.getGestorTrayectoria().getFecha().toString();

                String[] datos = {casillaInicio, casillaMeta,distancia, fecha};
                writer.writeNext(datos);
            }

            System.out.println("CSV generado con éxito en: " + this.excel3);
        } catch (IOException e) {
            throw new IOException("Error al escribir el archivo CSV", e);
        }
    }
    /////////////////////////////////////////////////////////////////////////

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
