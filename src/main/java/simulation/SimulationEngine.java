package simulation;

import elements.Animal;
import elements.Vector2d;
import maps.AbstractWorldMap;

import java.util.List;

public class SimulationEngine implements IEngine{
    List<Animal> animals;
    private AbstractWorldMap map;

    public SimulationEngine(AbstractWorldMap map, Vector2d[] animalsPositions, int copulationEnergy, int copulationLossEnergy, int grassPerDay) {
        this.map = map;
        this.map.setCopulationEnergy(copulationEnergy);
        this.map.setCopulationLossEnergy(copulationLossEnergy);
        this.map.setGrassPerDay(grassPerDay);
        this.animals = map.getAnimals();

        for (Vector2d animalsPosition : animalsPositions) {
            Animal animal = new Animal(animalsPosition, (int) (Math.random() * 40) + 20);
            map.place(animal);
            System.out.println(animal.getPosition() + " " + animal.energy);
        }
        map.place(new Animal(new Vector2d(3,3), 0));
        System.out.println(map);
    }

    @Override
    public void run() {
        map.nextDay();
    }
}