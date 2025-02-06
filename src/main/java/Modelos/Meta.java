package Modelos;

public class Meta extends Casilla {
    public Meta(int x, int y) {
        super(x, y, true); // La meta siempre está activa
    }

    @Override
    public String toString() {
        return "Meta(" + getX() + ", " + getY() + ")";
    }
}