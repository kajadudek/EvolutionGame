package elements;

public class Animal {
    private Vector2d position;

    public Animal(Vector2d initialPosition){
        this.position = initialPosition;
    }
    public Vector2d getPosition() {
        return this.position;
    }
}
