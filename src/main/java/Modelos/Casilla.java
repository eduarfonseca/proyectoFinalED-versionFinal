package Modelos;

public class Casilla {
    private final int x;
    private final int y;
    private boolean activa;

    public Casilla(int x, int y, boolean activa) {
        this.x = x;
        this.y = y;
        this.activa = activa;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    @Override
    public String toString() {
        return "Casilla(" + x + ", " + y + ", " + (activa ? "activa" : "inactiva") + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Casilla casilla = (Casilla) o;
        return x == casilla.x && y == casilla.y;
    }
}