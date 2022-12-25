package maps;

import elements.Animal;
import elements.AnimalElement.MoveDirection;
import elements.Vector2d;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import simulation.SimulationVariables;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GlobeMapTest {

    @Test
    public void animalMoveOnMapTest() throws IOException, ParseException {
        WorldMap map = new WorldMap(new SimulationVariables());
        Animal animal1 = new Animal(new Vector2d(map.mapWidth+1, 3), 30);
        Animal animal2 = new Animal(new Vector2d(2, map.mapHeight+1), 30);
        Animal animal3 = new Animal(new Vector2d(2, -1), 30);

        map.place(animal1);
        map.place(animal2);
        map.place(animal3);

        map.map = new GlobeMap();
        map.map.animalMoveOnMap(map);

        assertEquals(animal1.getPosition(), new Vector2d(0,3));
        assertEquals(animal2.getPosition(), new Vector2d(2,map.mapHeight));
        assertEquals(animal2.orientation, MoveDirection.S);
        assertEquals(animal3.getPosition(), new Vector2d(2, 0));
        assertEquals(animal3.orientation, MoveDirection.N);
    }

}