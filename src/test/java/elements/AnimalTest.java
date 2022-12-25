package elements;

import elements.AnimalElement.MoveDirection;
import elements.AnimalElement.Predestination;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    public void moveTest(){
        Animal animal = new Animal(new Vector2d(3,4), 40);
        animal.setAnimalBehavior(new Predestination());
        animal.genotype.add(0);
        animal.genotype.add(5);
        animal.genotype.add(7);

        animal.move();
        assertEquals(animal.getPosition(), new Vector2d(3,5));
        assertEquals(animal.orientation, MoveDirection.N);

        animal.move();
        assertEquals(animal.getPosition(), new Vector2d(2,4));
        assertEquals(animal.orientation, MoveDirection.SW);

        animal.move();
        assertEquals(animal.getPosition(), new Vector2d(2, 3));
        assertEquals(animal.orientation, MoveDirection.S);

        animal.move();
        assertEquals(animal.getPosition(), new Vector2d(2, 2));
        assertEquals(animal.orientation, MoveDirection.S);
    }
}