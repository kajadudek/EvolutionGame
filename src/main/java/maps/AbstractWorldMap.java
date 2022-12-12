package maps;

import elements.Animal;
import elements.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.ceil;


abstract public class AbstractWorldMap implements IWorldMap {
    protected List<Animal> animals = new ArrayList<>();
    private Map<Vector2d, Integer> emptyGreenBeltFields = new HashMap<>();
    private Map<Vector2d, Integer> emptyNonGreenFields = new HashMap<>();

    private int copulationEnergy, grassPerDay, greenLowerY, greenUpperY;
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
    public void setGrassPerDay(int grassPerDay) { this.grassPerDay = grassPerDay; }

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

    /**
     * Calculates 20% of map and its boundaries for green belt
     */
    private void calculateGreenBelt() {
        int greenHeight = ((int)ceil(0.2 * (mapHeight+1) * (mapWidth+1))) / (mapWidth+1);

        // If greenHeight(mod mapWidth) >=5 then adding one moge greenBelt would be closer to 20% of the map
        if (((int)ceil(0.2 * (mapHeight+1) * (mapWidth+1))) % (mapWidth+1) >= 5) { greenHeight += 1;}

        if ((mapHeight + 1)%2 == 0){
            this.greenLowerY = (mapHeight + 1)/2 - greenHeight/2;
        }else {
            this.greenLowerY = (mapHeight)/2 - greenHeight/2;
        }
        this.greenUpperY = greenLowerY + greenHeight - 1;

        //Initialize empty fields on greenBelt
        for (int y = greenLowerY; y <= greenUpperY; y ++){
            for (int x = 0; x <= mapWidth; x++) {
                emptyGreenBeltFields.put(new Vector2d(x,y),0);
            }
        }

        //Initialize empty fields outside greenBelt
        for (int y = 0; y <= mapHeight; y ++){
            if ((y >=0 && y < greenLowerY) || (y > greenUpperY && y <= mapHeight)){
                for (int x = 0; x <= mapWidth; x++) {
                    emptyNonGreenFields.put(new Vector2d(x,y),0);
                }
            }
        }
    }

    private void greenGrow(int howManyGreen) {
        for (int x=0; x < howManyGreen; x++) {
            int randomField = (int)(Math.random()*10);

            // 20% chance to spawn Grass outside the green belt
            if (randomField < 2) {
                if(emptyNonGreenFields.size() > 0){
                    //TODO - remove non-empty fields
                    //     - randomly pick field for grass growth with the least death count
                }
            // 80% chance to spawn Grass inside the green belt
            }else {
                if (emptyGreenBeltFields.size() > 0){
                    //TODO - remove non-empty fields
                    //     - randomly pick field for grass growth with the least death count
                }
            }
        }
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
     * Updates number of deaths on certain position
     * @param position
     */
    private void updateToxicFields(Vector2d position) {
        if (position.y <= greenUpperY && position.y >= greenLowerY){
            emptyGreenBeltFields.put(position,emptyGreenBeltFields.get(position) + 1);
        }else {
            emptyNonGreenFields.put(position,emptyNonGreenFields.get(position) + 1);
        }
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
//        System.out.println(emptyGreenBeltFields);
//        System.out.println(emptyNonGreenFields);
    }
}
