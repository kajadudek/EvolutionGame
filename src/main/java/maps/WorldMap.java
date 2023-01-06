package maps;

import elements.Animal;
import elements.Grass;
import elements.Vector2d;
import interfaces.*;
import main.CsvWriter;
import simulation.SimulationVariables;

import java.util.*;

public class WorldMap implements IWorldMap {
    protected List<Animal> animals = new ArrayList<>();
    protected List<Grass> grasses = new ArrayList<>();
    List<Vector2d> emptyGreenBeltField = new ArrayList<>();
    List<Vector2d> emptyNonGreenField = new ArrayList<>();
    Map<Vector2d, Integer> deathCounter = new HashMap<>();
    public Map<List<Integer>, Integer> genotypes = new HashMap<>();
    int copulationEnergy, grassPerDay, greenLowerY, greenUpperY;
    protected int copulationLossEnergy;
    MapVisualizer mapVisualizer = new MapVisualizer(this);
    public int mapHeight, mapWidth, eatingEnergy, day;
    public IMapType map;
    public IPlantFields greenFields;
    public IAnimalBehavior animalBehavior;
    public IGenotypeMutation genotypeMutation;
    public final SimulationVariables settings;

    // Stats
    public int animalsOnMap = 0;
    public int deadAnimals = 0;
    public int grassesOnMap = 0;
    public int emptyFields = 0;
    private int animalsEnergySum = 0;
    public int avgAnimalsEnergy = 0;
    private int deadAnimalsAgeSum = 0;
    public int avgDeadAnimalsAge = 0;
    public CsvWriter toCSVWriter;


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
        this.animalsOnMap = settings.animalsOnStart;
        this.toCSVWriter = new CsvWriter();
        this.day = 0;

        this.greenFields.calculateGreenFields(this);
    }

    public List<Animal> getAnimals() {
        return animals;
    }
    public List<Grass> getGrasses() { return grasses; }
    public List<List<Integer>> getPopularGenotypes() { return getMostPopularGenotype(); }

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
        if (genotypes.containsKey(animal.genotype)){
            genotypes.put(animal.genotype, genotypes.get(animal.genotype) + 1);
        } else {
            genotypes.put(animal.genotype, 1);
        }
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
        Animal result = animalList.get(0);
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
            if (animal.getEnergy() == maxiEnergy){
                resultList.add(animal);
            }
        }

        // if there is more than one animal with max energy, we compare its age
        if (resultList.size() > 1){
            animalList = new ArrayList<>();

            // find maximum age from list of animals
            for (Animal animal: resultList){
                maxiAge = Math.max(maxiAge, animal.getAge());
            }

            for (Animal animal: resultList){
                if (animal.getAge() == maxiAge){
                    animalList.add(animal);
                }
            }

            // if there is more than one animal with max age, we compare its children
            if (animalList.size() > 1){
                resultList = new ArrayList<>();

                // find maximum childrenCounter from list of animals
                for (Animal animal: animalList){
                    maxiChildren = Math.max(maxiChildren, animal.getChildCounter());
                }

                for (Animal animal: animalList){
                    if (animal.getChildCounter() == maxiChildren){
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

        return result;
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
                            && animal.getEnergy() >= this.copulationEnergy){

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

                    mother.setEnergy(mother.getEnergy() - copulationLossEnergy);
                    father.setEnergy(father.getEnergy() - copulationLossEnergy);

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
            if (animal.getEnergy() <= 0) {
                animalsToRemove.add(animal);
                if (this.greenFields instanceof ToxicFields) {
                    updateToxicFields(animal.getPosition());
                }
            }
        }

        if(animalsToRemove.size() > 0){
            for (Animal animal: animalsToRemove) {
                this.deadAnimalsAgeSum += animal.getAge();
                // For most popular genotype stats
                if (this.genotypes.get(animal.genotype) == 1){
                    this.genotypes.remove(animal.genotype);
                } else {
                    this.genotypes.put(animal.genotype, genotypes.get(animal.genotype) - 1);
                }
                this.animals.remove(animal);
                this.deadAnimals += 1;
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
                animalWillEat.setEnergy(animalWillEat.getEnergy() + this.eatingEnergy);
                animalWillEat.grassesEaten += 1;
                grassesToRemove.add(grass);
            }
            else if (hungryAnimals.size() == 1){
                grassesToRemove.add(grass);
                hungryAnimals.get(0).setEnergy(hungryAnimals.get(0).getEnergy() + this.eatingEnergy);
                hungryAnimals.get(0).grassesEaten += 1;
            }
        }

        for (Grass grass: grassesToRemove){
            if (grass.position.y <= this.greenUpperY && grass.position.y >= this.greenLowerY) {
                this.emptyGreenBeltField.add(grass.position);
            } else {
                this.emptyNonGreenField.add(grass.position);
            }

            this.grasses.remove(grass);
            this.grassesOnMap -= 1;
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
        removeAnimals();
        this.day += 1;
        this.animalsOnMap = 0;
        this.animalsEnergySum = 0;
        eatGrass();
        copulation();
        for(Animal animal: animals){
            this.animalsOnMap += 1;
            this.animalsEnergySum += animal.getEnergy();
            animal.move();
        }
        this.map.animalMoveOnMap(this);
        this.greenFields.greenGrow(this, this.grassPerDay);
        this.getAvgAnimalsEnergy();
        this.getEmptyFields();
        this.getAvgDeadAnimalsAge();

        if (!this.settings.fileToExportName.equals("")){
            this.saveToCSV();
        }
    }

    /**
     * Handle stats count
     */

    private void getAvgAnimalsEnergy() {
        if (this.animalsOnMap > 0){
            this.avgAnimalsEnergy = this.animalsEnergySum/this.animalsOnMap;
        } else {
            this.avgAnimalsEnergy = 0;
        }
    }

    private void getEmptyFields() {
        int fieldsOnMap = (this.mapWidth + 1) * (this.mapHeight + 1);

        for (int i=0; i <= this.mapWidth; i++) {
            for (int j=0; j <= this.mapHeight; j++) {
                Vector2d pos = new Vector2d(i,j);

                if (this.objectAt(pos) != null) {
                    fieldsOnMap -= 1;
                }
            }
        }
        this.emptyFields = fieldsOnMap;
    }

    private void getAvgDeadAnimalsAge() {
        if (this.deadAnimals > 0){
            this.avgDeadAnimalsAge= this.deadAnimalsAgeSum/this.deadAnimals;
        } else {
            this.avgDeadAnimalsAge = 0;
        }
    }

    private List<List<Integer>> getMostPopularGenotype(){
        int maxAnimalsWithTheSameGenotype = 0;
        List<List<Integer>> mostPopularGenotypesList = new ArrayList<>();

        for (Map.Entry<List<Integer>, Integer> entry : genotypes.entrySet()) {
            maxAnimalsWithTheSameGenotype = Math.max(maxAnimalsWithTheSameGenotype, entry.getValue());
        }

        int howManyGenotypes = 0;
        for (Map.Entry<List<Integer>, Integer> entry : genotypes.entrySet()) {
            if (maxAnimalsWithTheSameGenotype == entry.getValue()){
                mostPopularGenotypesList.add(entry.getKey());
                howManyGenotypes += 1;
            }
            if (howManyGenotypes >=2 ){ break; }
        }

        return mostPopularGenotypesList;
    }


    /**
     * Save stats to CSV file
     */

    private void saveToCSV(){
        String secondGenotype = "none";
        String secondGenotypeAnimals = "none";
        if (this.getPopularGenotypes().size() > 1){
            secondGenotype = this.getPopularGenotypes().get(1).toString();
            secondGenotypeAnimals = String.valueOf(this.genotypes.get(this.getPopularGenotypes().get(1)));
        }

        String[] dailyStats = {String.valueOf(this.day), String.valueOf(this.animalsOnMap),
                String.valueOf(this.grassesOnMap), String.valueOf(this.emptyFields),
                this.getPopularGenotypes().get(0).toString(),
                String.valueOf(this.genotypes.get(this.getPopularGenotypes().get(0))),
                secondGenotype, secondGenotypeAnimals, String.valueOf(this.avgAnimalsEnergy),
                String.valueOf(this.avgDeadAnimalsAge)};

        toCSVWriter.createCsvData(dailyStats);
    }
}