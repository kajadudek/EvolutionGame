package maps;

import elements.Animal;
import elements.AnimalElement.MoveDirection;
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
                animal.position = new Vector2d(0, animal.position.y);
            }
            if (animal.getPosition().x < 0) {;
                animal.position = new Vector2d(mapWidth, animal.position.y);
            }
            if (position.y > mapHeight) {
                animal.position = new Vector2d(animal.position.x, mapHeight);
                animal.orientation = MoveDirection.S;
            }
            if (position.y < 0) {
                animal.position = new Vector2d(animal.position.x, 0);
                animal.orientation = MoveDirection.N;

            }
        }
    }
}
