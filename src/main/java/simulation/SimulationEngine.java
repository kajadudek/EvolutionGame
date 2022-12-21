package simulation;

import elements.Animal;
import elements.Vector2d;
import interfaces.IEngine;
import maps.WorldMap;

import java.util.ArrayList;
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

            List<Integer> genotype = new ArrayList<>();
            genotype.add(0);
            genotype.add(0);
            genotype.add(7);
            genotype.add(5);

            Animal animal = new Animal(animalsPosition, (int) (Math.random() * 40) + 20, genotype);
            animal.setAnimalBehavior(settings.animalBehavior);
            map.place(animal);
            System.out.println(animal.getPosition() + " " + animal.energy);
        }
        List<Integer> genotype2 = new ArrayList<>();
        genotype2.add(1);
        genotype2.add(4);
        genotype2.add(3);
        genotype2.add(2);

        Animal animal = new Animal(new Vector2d(1,2), 934, genotype2);
        animal.setAnimalBehavior(settings.animalBehavior);
        map.place(animal);
        map.place(new Animal(new Vector2d(3,3), 0));
        System.out.println(map);
    }

    @Override
    public void run() {
        map.removeAnimals();
        for (int i=0; i < 10; i++){
            for(Animal animal: animals) {
//                System.out.print(animal.position + " -> ");
                animal.move();
//                System.out.println(animal.position);
            }
            map.nextDay();
            System.out.println(map);
        }
    }
}