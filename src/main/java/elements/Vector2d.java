package elements;

import java.util.Objects;

public class Vector2d {
    public int x;
    public int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    boolean precedes(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    boolean follows(Vector2d other) {
        return other.x >= this.x && other.y >= this.y;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    @Override
    public boolean equals(Object other) {
        if ( this == other ) {
            return true;
        }
        if (!(other instanceof Vector2d)) {
            return false;
        }
        Vector2d that = (Vector2d) other;
        return that.x == this.x && that.y == this.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.x,this.y);
    }
}
