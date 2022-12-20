package maps;

import elements.Animal;
import elements.Vector2d;
import interfaces.IMapType;
import simulation.SimulationVariables;

import java.util.List;
import java.util.Map;

/**
 * Map that enables animals to cross the border and appear on its opposite side.
 **/
public class GlobeMap implements IMapType {
    @Override
    public void animalMoveOnMap(WorldMap map) {
        int mapWidth = map.mapWidth;
        int mapHeight = map.mapHeight;
        List<Animal> animals = map.getAnimals();

        for (Animal animal: animals) {

            Vector2d position = animal.getPosition();

            if (animal.getPosition().x > mapWidth) {
                animal.position = new Vector2d(0, position.y);
            } else if (animal.getPosition().x < 0) {
                animal.position = new Vector2d(mapWidth - 1, position.y);
            } else if (position.y > mapHeight) {
                animal.position = new Vector2d(position.x, 0);
            } else if (position.y < 0) {
                animal.position = new Vector2d(position.x, mapHeight - 1);
            }
        }
    }
}
