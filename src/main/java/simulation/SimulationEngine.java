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
    public SimulationEngine(SimulationVariables settings){
        this.map = new WorldMap(settings);
        this.map.setCopulationLossEnergy(settings.copulationLossEnergy);
        this.map.setCopulationEnergy(settings.copulationMinEnergy);
        this.animals = map.getAnimals();

        // Generate first animals
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
        int i = 0;

        // warunek i < 30 potrzebne po to, by zakończyc działanie programu po pewnym czasie.
        // Docelowo symulacja trwa dopóki są żyjące zwierzęta
        while (!animals.isEmpty() && i < 30){
                map.nextDay();
                System.out.println(map);
                i++;
        }
    }
}