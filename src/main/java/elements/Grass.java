package elements;

import interfaces.IPlantFields;
import maps.WorldMap;

public class Grass implements IPlantFields {
    public Vector2d position;

    public Grass(Vector2d position){
        this.position = position;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String toString(){
        return "*";
    }

    @Override
    public void calculateGreenFields(WorldMap map) {

    }

    @Override
    public void greenGrow(WorldMap map, int greenPerDay) {

    }
}
