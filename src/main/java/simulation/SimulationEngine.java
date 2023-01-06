package simulation;

import elements.Animal;
import gui.App;
import interfaces.IEngine;
import maps.WorldMap;

import java.util.List;

public class SimulationEngine implements IEngine, Runnable {
    List<Animal> animals;
    public WorldMap map;
    private App app;
    public Thread t;
    private boolean paused = false;


    public SimulationEngine(SimulationVariables settings, App app){
        this.map = new WorldMap(settings);
        this.map.setCopulationLossEnergy(settings.copulationLossEnergy);
        this.map.setCopulationEnergy(settings.copulationMinEnergy);
        this.animals = map.getAnimals();
        this.app = app;

        t = new Thread(this);
        // Generate first animals
        for (int i=0; i<settings.animalsOnStart; i++) {
            Animal animal = new Animal(settings.startEnergy);
            animal.generatePosition(this.map);
            animal.generateGenotype(settings.genotypeSize);
            animal.setAnimalBehavior(settings.animalBehavior);
            map.place(animal);
        }
    }

    @Override
    public void run() {
        try{
            Thread.sleep(250);
            while (!animals.isEmpty()){
                pause();
                map.nextDay();
                this.app.animation();
                Thread.sleep(250);
            }
        }
        catch (InterruptedException e) {
            e.getMessage();
        }
    }

    public boolean isPaused(){
        return paused;
    }

    public void setPause(){
        this.paused = true;
    }

    public void pause(){
        synchronized (this) {
            while (this.paused) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void resume(){
        this.paused = false;
        synchronized (this) {
            notify();
        }
    }
}