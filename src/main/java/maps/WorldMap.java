package maps;

import elements.Animal;
import elements.Grass;
import elements.ToxicField;
import elements.Vector2d;
import interfaces.*;
import simulation.SimulationVariables;

import java.util.*;

public class WorldMap implements IWorldMap {
    protected List<Animal> animals = new ArrayList<>();
    protected List<Grass> grasses = new ArrayList<>();
    List<Vector2d> emptyGreenBeltField = new ArrayList<>();
    List<Vector2d> emptyNonGreenField = new ArrayList<>();
    Map<Vector2d, Integer> deathCounter = new HashMap<>();
    int copulationEnergy, grassPerDay, greenLowerY, greenUpperY;
    protected int copulationLossEnergy;
    MapVisualizer mapVisualizer = new MapVisualizer(this);
    public int mapHeight;
    public int mapWidth;
    public IMapType map;
    public IPlantFields greenFields;
    public IAnimalBehavior animalBehavior;
    public IGenotypeMutation genotypeMutation;
    public int eatingEnergy;
    public final SimulationVariables settings;

    public WorldMap(SimulationVariables settings) {
        this.settings = settings;
        this.mapHeight = settings.mapHeight;
        this.mapWidth = settings.mapWidth;
        this.map = (IMapType) settings.mapType;
        this.greenFields = (IPlantFields) settings.plantFields;
        this.grassPerDay = settings.grassPerDay;
        this.animalBehavior = settings.animalBehavior;
        this.genotypeMutation = settings.genotypeMutation;
        this.eatingEnergy = settings.eatingEnergy;

        this.greenFields.calculateGreenFields(this);
    }

    public List<Animal> getAnimals() {
        return animals;
    }
    public List<Grass> getGrasses() { return grasses;}

    public void setCopulationEnergy(int copulationEnergy) {
        this.copulationEnergy = copulationEnergy;
    }

    public void setCopulationLossEnergy(int copulationLossEnergy){
        this.copulationLossEnergy = copulationLossEnergy;
    }

    @Override
    public String toString(){
        return mapVisualizer.draw(new Vector2d(0,0), new Vector2d(this.mapWidth,this.mapHeight));
    }

    @Override
    public void place(Animal animal) {
        animals.add(animal);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animals) {
            if (animal.getPosition().equals(position)) {
                return animal;
            }
        }
        for (Grass grass : grasses) {
            if (grass.getPosition().equals(position)) {
                return grass;
            }
        }
        return null;
    }

    public Object isGrassThere(Vector2d position) {
        for (Grass grass : grasses) {
            if (grass.getPosition().equals(position)) {
                return grass;
            }
        }
        return null;
    }

    /**
     * Finds the strongest animal from the ones, that are on the same field.
     * @param animalList
     * @return
     */
    public Animal theStrongestAnimal(List<Animal> animalList){
        int maxiEnergy = -1;
        int maxiAge = -1;
        int maxiChildren = -1;
        List<Animal> resultList = new ArrayList<>();

        if (animalList.size() == 1){
            return animalList.get(0);
        }

        // find maximum energy from list of animals
        for (Animal animal: animalList){
            maxiEnergy = Math.max(maxiEnergy, animal.getEnergy());
        }

        // check which animals have max energy
        for (Animal animal: animalList){
            if (animal.energy == maxiEnergy){
                System.out.println(animal.position + " " + animal.energy);
                resultList.add(animal);
            }
        }

        // if there is more than one animal with max energy, we compare its age
        if (resultList.size() > 1){
            animalList = new ArrayList<>();

            // find maximum age from list of animals
            for (Animal animal: resultList){
                maxiAge = Math.max(maxiAge, animal.age);
            }

            for (Animal animal: resultList){
                if (animal.age == maxiAge){
                    animalList.add(animal);
                }
            }

            // if there is more than one animal with max age, we compare its children
            if (animalList.size() > 1){
                resultList = new ArrayList<>();

                // find maximum childrenCounter from list of animals
                for (Animal animal: animalList){
                    maxiChildren = Math.max(maxiChildren, animal.childCounter);
                }

                for (Animal animal: animalList){
                    if (animal.childCounter == maxiChildren){
                        resultList.add(animal);
                    }
                }

                // if there is more than one animal with max children, we get the first one from the array
                if (resultList.size() >= 1){
                    return resultList.get(0);
                }

            } else if (animalList.size() == 1){
                return animalList.get(0); }

        } else if (resultList.size() == 1) {
            return resultList.get(0); }

        return null;
    }

    /**
     * Handle the copulation of animals on the map.
     */
    private void copulation() {
        for (int x=0; x <= mapWidth; x++ ){
            for (int y=0; y <= mapHeight; y++){

                Vector2d currentPos = new Vector2d(x,y);
                ArrayList<Animal> possibleParents = new ArrayList<>();

                for(Animal animal: animals){
                    if (animal.getPosition().equals(currentPos)
                            && animal.energy >= this.copulationEnergy){

                        possibleParents.add(animal);
                    }
                }

                if (possibleParents.size() >= 2){
                    Animal mother;
                    Animal father;

                    if (possibleParents.size() == 2) {
                        mother = possibleParents.get(0);
                        father = possibleParents.get(1);
                    } else {
                        mother = theStrongestAnimal(possibleParents);
                        possibleParents.remove(mother);
                        father = theStrongestAnimal(possibleParents);
                    }

                    int childEnergy = copulationLossEnergy * 2;

                    mother.energy -= copulationLossEnergy;
                    father.energy -= copulationLossEnergy;

                    Animal child = new Animal(mother.getPosition(), childEnergy);
                    child.setAnimalBehavior(this.animalBehavior);
                    child.createGenotype(mother, father);
                    this.genotypeMutation.genotypeMutation(child, this);

                    this.place(child);
                }
            }
        }
    }

    /**
     * Updates number of deaths on certain position
     * @param position
     */
    private void updateToxicFields(Vector2d position) {
        deathCounter.put(position, deathCounter.get(position) + 1);
    }

    /**
     * Removes dead animals from map, and collects data about poisoned fields.
     */
    public void removeAnimals() {
        List<Animal> animalsToRemove = new ArrayList<>();

        for (Animal animal: animals) {
            if (animal.getEnergy() == 0) {
                animalsToRemove.add(animal);
                if (this.greenFields instanceof ToxicFields) {
                    updateToxicFields(animal.getPosition());
                }
            }
        }

        if(animalsToRemove.size() > 0){
            for (Animal animal: animalsToRemove) {
                this.animals.remove(animal);
                System.out.println(this.animals);
            }
        }
    }

    /**
     * Handle eating grasses. Increase animal energy and removes grass from map.
     */
    public void eatGrass(){
        List<Grass> grassesToRemove = new ArrayList<>();

        for (Grass grass: grasses){
            List<Animal> hungryAnimals = new ArrayList<>();
            for (Animal animal: animals){
                if (grass.getPosition().equals(animal.getPosition())){
                    hungryAnimals.add(animal);
                }
            }

            if (hungryAnimals.size()>1){
                Animal animalWillEat = theStrongestAnimal(hungryAnimals);
                animalWillEat.energy += this.eatingEnergy;
                grassesToRemove.add(grass);
            }
            else if (hungryAnimals.size() == 1){
                grassesToRemove.add(grass);
                hungryAnimals.get(0).energy += this.eatingEnergy;
            }
        }

        for (Grass grass: grassesToRemove){
            this.grasses.remove(grass);
        }
    }

    /**
     * Handle day schedule:
     *  - removing dead animals from map
     *  - moving animals
     *  - eating grass
     *  - copulating
     *  - grass growth
     */
    public void nextDay(){
        System.out.println("nastepny dzien");
        removeAnimals();
        for(Animal animal: animals){
            animal.move();
        }
        eatGrass();
        copulation();
        this.map.animalMoveOnMap(this);
        this.greenFields.greenGrow(this, this.grassPerDay);
    }
}