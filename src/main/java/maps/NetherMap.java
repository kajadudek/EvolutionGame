package maps;

import elements.Animal;
import elements.Vector2d;
import interfaces.IMapType;

import java.util.List;

/**
 * Map that enables animals to cross the border and loose part of its energy and appear on randomly generated place.
 **/
public class NetherMap implements IMapType {

    private Vector2d generatePosition(int mapHeight, int mapWidth) {
        int x = (int) (Math.random() * mapWidth);
        int y = (int) (Math.random() * mapHeight);
        return new Vector2d(x, y);
    }

    @Override
    public void animalMoveOnMap(WorldMap map) {
        int mapWidth = map.mapWidth;
        int mapHeight = map.mapHeight;
        List<Animal> animals = map.getAnimals();
        int copulationLossEnergy = map.copulationLossEnergy;

        for (Animal animal: animals) {

            Vector2d oldPosition = animal.getPosition();
            Vector2d newPosition = generatePosition(mapHeight, mapWidth);

            if (animal.getPosition().x > mapWidth
                    || animal.getPosition().x < 0
                    || oldPosition.y > mapHeight
                    || oldPosition.y < 0) {
                animal.position = newPosition;
                animal.energy = animal.energy - copulationLossEnergy;
            }
        }
    }
}
