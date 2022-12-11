package maps;

import elements.Animal;
import elements.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static java.lang.Math.ceil;


abstract public class AbstractWorldMap implements IWorldMap {
    protected List<Animal> animals = new ArrayList<>();
    private int copulationEnergy;
    protected int copulationLossEnergy;
    MapVisualizer mapVisualizer = new MapVisualizer(this);
    public final int mapHeight, mapWidth;

    abstract public void animalMoveOnMap();

    protected AbstractWorldMap(int mapHeight, int mapWidth) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;

        calculateGreenBelt();
    }

    public List<Animal> getAnimals() {
        return animals;
    }

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
        return null;
    }

    private void calculateGreenBelt() {
        int greenHeight = (int)ceil(0.2 * mapHeight);

        // Green belt borders
        int greenLowerX;
        int greenUpperX;

        if ((mapHeight + 1)%2 == 0){
            if (greenHeight % 2 == 1) {
                greenHeight += 1;
            }
            greenLowerX = (mapHeight + 1)/2 - greenHeight/2;
        }else {
            greenLowerX = (mapHeight)/2 - greenHeight/2;
            if (greenHeight%2==0){
                greenHeight += 1;
            }
        }
        // Upper x boundary of green belt = lower border + its height
        greenUpperX = greenLowerX + greenHeight - 1;
    }

    /**
     * Handle the copulation of animals on the map.
     */
    public void copulation() {
        for (int x=0; x <= mapWidth; x++ ){
            for (int y=0; y <= mapHeight; y++){

                Vector2d currentPos = new Vector2d(x,y);
                ArrayList<Animal> possibleParents = new ArrayList<>();

                for(Animal animal: animals){
                    if (animal.getPosition().equals(currentPos)
                            && animal.energy>= this.copulationEnergy){

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
     * Removes dead animals from map, and collects data about poisoned fields.
     */
    public void removeAnimals() {
        for (Animal animal: animals) {
            if (animal.getEnergy() == 0) {
                animals.remove(animal);
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
        animalMoveOnMap();
        copulation();
    }
}
