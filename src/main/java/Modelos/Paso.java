package Modelos;

public class Paso {
    private final int pasoNumero;
    private final int fila;
    private final int columna;
    private final String mensaje;

    public Paso(int pasoNumero, int fila, int columna, String mensaje) {
        this.pasoNumero = pasoNumero;
        this.fila = fila;
        this.columna = columna;
        this.mensaje = mensaje;
    }

    public int getPasoNumero() {
        return pasoNumero;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public String getMensaje() {
        return mensaje;
    }

    @Override
    public String toString() {
        return "Paso " + pasoNumero + ": (" + fila + ", " + columna + ") - " + mensaje;
    }
}

