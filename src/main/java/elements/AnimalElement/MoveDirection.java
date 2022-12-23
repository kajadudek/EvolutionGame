package elements.AnimalElement;

public enum MoveDirection {
    N,
    NE,
    E,
    SE,
    S,
    SW,
    W,
    NW;

    @Override
    public String toString() {
        return switch (this) {
            case N -> "N";
            case NE -> "NE";
            case E -> "E";
            case SE -> "SE";
            case S -> "S";
            case SW -> "SW";
            case W -> "W";
            case NW -> "NW";
        };
    }

    public int getNumericValue(){
        return switch (this) {
            case N -> 0;
            case NE -> 1;
            case E -> 2;
            case SE -> 3;
            case S -> 4;
            case SW -> 5;
            case W -> 6;
            case NW -> 7;
        };
    }
}
