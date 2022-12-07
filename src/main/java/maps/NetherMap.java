package maps;

import elements.Animal;
import elements.Vector2d;

import java.util.Map;

/**
 * Map that enables animals to cross the border and loose part of its energy and appear on randomly generated place.
 **/
public class NetherMap extends AbstractWorldMap {
    protected NetherMap(int mapHeight, int mapWidth) {
        super(mapHeight, mapWidth);
    }

    //TODO - Decrease animal's energy after generating its position

    private Vector2d generatePosition() {
        int x = (int) (Math.random() * mapWidth);
        int y = (int) (Math.random() * mapHeight);
        return new Vector2d(x, y);
    }

    public void animalMoveOnMap() {
        for (Map.Entry<Vector2d, Animal> set : animals.entrySet()) {

            Animal animal = set.getValue();
            Vector2d oldPosition = animal.getPosition();
            Vector2d newPosition = generatePosition();

            if (animal.getPosition().x > mapWidth) {
                animals.remove(oldPosition);
                animal.energy = animal.energy/2;
                animals.put(newPosition, animal);
            } else if (animal.getPosition().x < 0) {
                animals.remove(oldPosition);
                animal.energy = animal.energy/2;
                animals.put(newPosition, animal);
            } else if (oldPosition.y > mapHeight) {
                animals.remove(oldPosition);
                animal.energy = animal.energy/2;
                animals.put(newPosition, animal);
            } else if (oldPosition.y < 0) {
                animals.remove(oldPosition);
                animal.energy = animal.energy/2;
                animals.put(newPosition, animal);
            }
        }
    }
}
