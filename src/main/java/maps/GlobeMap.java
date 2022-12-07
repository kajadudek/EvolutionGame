package maps;

import elements.Animal;
import elements.Vector2d;

import java.util.Map;

/**
 * Map that enables animals to cross the border and appear on its opposite side.
 **/
public class GlobeMap extends AbstractWorldMap {
    protected GlobeMap(int mapHeight, int mapWidth) {
        super(mapHeight, mapWidth);
    }

    public void animalMoveOnMap() {
        for (Map.Entry<Vector2d, Animal> set : animals.entrySet()) {

            Animal animal = set.getValue();
            Vector2d position = animal.getPosition();

            if (animal.getPosition().x > mapWidth) {
                animals.remove(position);
                animals.put(new Vector2d(0, position.y), animal);
            } else if (animal.getPosition().x < 0) {
                animals.remove(position);
                animals.put(new Vector2d(mapWidth - 1, position.y), animal);
            } else if (position.y > mapHeight) {
                animals.remove(position);
                animals.put(new Vector2d(position.x, 0), animal);
            } else if (position.y < 0) {
                animals.remove(position);
                animals.put(new Vector2d(position.x, mapHeight - 1), animal);
            }
        }
    }
}
