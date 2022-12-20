package simulation;

import elements.Animal;
import elements.Vector2d;
import interfaces.IEngine;
import maps.WorldMap;

import java.util.List;

public class SimulationEngine implements IEngine {
    List<Animal> animals;
    private WorldMap map;
    public SimulationEngine(SimulationVariables settings, Vector2d[] animalsPositions){
        this.map = new WorldMap(settings);
        this.map.setCopulationLossEnergy(settings.copulationLossEnergy);
        this.map.setCopulationEnergy(settings.copulationMinEnergy);
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
        map.removeAnimals();
        for (int i=0; i < 10; i++){
            for(Animal animal: animals) {
//                System.out.print(animal.position + " -> ");
                animal.getPosition().y += 1;
//                System.out.println(animal.position);
            }
            map.nextDay();
            System.out.println(map);
        }
    }
}