package maps;

import elements.Animal;
import elements.Grass;
import elements.Vector2d;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import simulation.SimulationVariables;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorldMapTest {
    @Test
    public void theStrongestAnimalTest() throws IOException, ParseException {
        WorldMap map = new WorldMap(new SimulationVariables());

        Animal animal1 = new Animal(new Vector2d(2,2), 45);
        animal1.childCounter = 4;
        animal1.age = 3;

        Animal animal2 = new Animal(new Vector2d(2,2), 45);
        animal2.childCounter = 5;
        animal2.age = 3;

        Animal animal3 = new Animal(new Vector2d(2,2), 45);
        animal3.childCounter = 4;
        animal3.age = 13;

        Animal animal4 = new Animal(new Vector2d(2,2), 46);
        animal4.childCounter = 4;
        animal4.age = 13;

        map.place(animal1);
        map.place(animal2);
        assertEquals(map.theStrongestAnimal(map.getAnimals()), animal2);

        map.place(animal3);
        assertEquals(map.theStrongestAnimal(map.getAnimals()), animal3);

        map.place(animal4);
        assertEquals(map.theStrongestAnimal(map.getAnimals()), animal4);
    }

    @Test
    public void isGrassThereTest() throws IOException, ParseException {
        WorldMap map = new WorldMap(new SimulationVariables());

        Grass grass = new Grass(new Vector2d(3,3));
        map.grasses.add(grass);

        assertTrue(map.isGrassThere(new Vector2d(3,3)) instanceof Grass);
        assertFalse(map.isGrassThere(new Vector2d(2,6)) instanceof Grass);
    }
}