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

        for (int i=0; i<settings.animalsOnStart; i++) {
            Animal animal = new Animal(settings.startEnergy);
            animal.generatePosition(this.map);
            animal.generateGenotype(settings.genotypeSize);
            animal.setAnimalBehavior(settings.animalBehavior);
            map.place(animal);
        }
        System.out.println(map);
    }

    @Override
    public void run() {
        map.removeAnimals();
//        for (int i=0; i < 10; i++){
        int i = 0;

        while (!animals.isEmpty() && i < 30){
                map.nextDay();
                System.out.println(map);
                i++;
            }
//        }
    }
}