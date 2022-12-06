package maps;

import elements.Animal;
import elements.Vector2d;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap {
    public final int mapHeight, mapWidth;
    protected Map<Vector2d, Animal> animals = new HashMap<>();

    protected AbstractWorldMap(int mapHeight, int mapWidth) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
    }

    @Override
    public void place(Animal animal) {
        animals.put(animal.getPosition(), animal);
    }

    @Override
    public Object objectAt(Vector2d position){
        if(animals.containsKey(position)){
            return animals.get(position);
        }
        return null;
    }
    @Override
    public boolean isOccupied(Vector2d position){
        return objectAt(position) != null;
    }


}
