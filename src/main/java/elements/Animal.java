package elements;

import maps.IWorldMap;

public class Animal {
    public Vector2d position;
    public int energy;

    public Animal(Vector2d initialPosition, int energy){
        this.energy = energy;
        this.position = initialPosition;
    }
    public Vector2d getPosition() {
        return this.position;
    }
    public int getEnergy() {
        return this.energy;
    }

    @Override
    public String toString() {
        return "X";
    }
}
