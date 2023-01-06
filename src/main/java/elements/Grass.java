package elements;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Grass {
    public Vector2d position;

    public Grass(Vector2d position){
        this.position = position;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public Rectangle paintObject(int size) {
       return new Rectangle(size,size, Color.rgb(109,155,78));
    }

    @Override
    public String toString(){
        return "* ";
    }
}
