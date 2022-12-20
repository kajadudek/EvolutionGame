package maps;

import elements.Animal;
import elements.Grass;
import elements.Vector2d;
import interfaces.IMapType;
import interfaces.IPlantFields;
import interfaces.IWorldMap;
import simulation.SimulationVariables;

import java.util.*;

import static java.lang.Math.ceil;

public class WorldMap implements IWorldMap {
    protected List<Animal> animals = new ArrayList<>();
    protected List<Grass> grasses = new ArrayList<>();
    List<Vector2d> emptyGreenBeltField = new ArrayList<>();
    List<Vector2d> emptyNonGreenField = new ArrayList<>();
    Map<Vector2d, Integer> deathCounter = new HashMap<>();
    int copulationEnergy, grassPerDay, greenLowerY, greenUpperY;
    private boolean growOnGreenBelt = true;
    protected int copulationLossEnergy;
    MapVisualizer mapVisualizer = new MapVisualizer(this);
    public int mapHeight;
    public int mapWidth;
    public IMapType map;
    public IPlantFields greenFields;
    public final SimulationVariables settings;

    public WorldMap(SimulationVariables settings) {
        this.settings = settings;
        this.mapHeight = settings.mapHeight;
        this.mapWidth = settings.mapWidth;
        this.map = (IMapType) settings.mapType;
        this.greenFields = (IPlantFields) settings.plantFields;
        this.grassPerDay = settings.grassPerDay;

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

    public boolean isGrassThere(Vector2d position) {
        for (Grass grass : grasses) {
            if (grass.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
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

                //TODO - IN ANIMAL CLASS
                //      method in animal that determine which two objects (from arrayList of objects) will copulate
                //      and which (only one) object can eat grass

                if (possibleParents.size() >= 2){
                    Animal mother = possibleParents.get(0);
                    Animal father = possibleParents.get(1);

                    int childEnergy = copulationLossEnergy * 2;

                    mother.energy -= copulationLossEnergy;
                    father.energy -= copulationLossEnergy;

                    //TODO - IN ANIMAL CLASS
                    //      creating child genome method

                    Animal child = new Animal(mother.getPosition(), childEnergy);

                    this.place(child);
                    System.out.println("urodzil sie dziecior z energia: " + child.energy);
                    System.out.println("z rodzicow: " + mother.energy + " " + father.energy);
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
                updateToxicFields(animal.getPosition());
            }
        }

        if(animalsToRemove.size() > 0){
            for (Animal animal: animalsToRemove) {
                this.animals.remove(animal);
                System.out.println(this.animals);
            }
        }
        //TODO - get places with the most animals' death counter. (Important for grass growth)
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
        copulation();
        this.map.animalMoveOnMap(this);
        this.greenFields.greenGrow(this, this.grassPerDay);
    }
}