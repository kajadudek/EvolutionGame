package maps;

import elements.Animal;
import elements.Vector2d;

import java.util.Map;

/**
 * Map that enables animals to cross the border and loose part of its energy and appear on randomly generated place.
 **/
public class NetherMap extends AbstractWorldMap {
    public NetherMap(int mapHeight, int mapWidth) {
        super(mapHeight, mapWidth);
    }

    private Vector2d generatePosition() {
        int x = (int) (Math.random() * mapWidth);
        int y = (int) (Math.random() * mapHeight);
        return new Vector2d(x, y);
    }

    @Override
    public void animalMoveOnMap() {
        for (Animal animal: animals) {

            Vector2d oldPosition = animal.getPosition();
            Vector2d newPosition = generatePosition();

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
