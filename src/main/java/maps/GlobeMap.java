package maps;

import elements.Animal;
import elements.Vector2d;

import java.util.Map;

/**
    Map that enables animals to cross the border and appear on its opposite side.
 **/
public class GlobeMap extends AbstractWorldMap{
    protected GlobeMap(int mapHeight, int mapWidth) {
        super(mapHeight, mapWidth);
    }

    //TODO - check if animal is going out of the map. Then adjust its position to the opposite side.

    public void animalOutOfBand() {
        for (Map.Entry<Vector2d, Animal> set:
                animals.entrySet()){
            Animal animal = set.getValue();

            if (animal.getPosition().x > mapWidth){
            }
        }
    }
}
