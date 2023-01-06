package elements;

import elements.AnimalElement.MoveDirection;
import interfaces.IAnimalBehavior;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import maps.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal {
    public Vector2d position;
    public int grassesEaten, idxOfGene;
    private int age, energy, childCounter;
    private boolean tracked = false, hasPopularGenotype = false;
    public MoveDirection orientation;
    public List<Integer> genotype = new ArrayList<>();
    IAnimalBehavior animalBehavior;

    // First animals with random genotype
    public Animal(int energy) {
        this.energy = energy;
        this.orientation = MoveDirection.values()[new Random().nextInt(MoveDirection.values().length)];
        this.age = 0;
        this.childCounter = 0;
        this.grassesEaten = 0;
    }

    // Animals that were born and inherit genotype from parents
    public Animal(Vector2d initialPosition, int energy) {
        this.energy = energy;
        this.position = initialPosition;
        this.orientation = MoveDirection.values()[new Random().nextInt(MoveDirection.values().length)];
        this.age = 0;
        this.childCounter = 0;
        this.grassesEaten = 0;
    }

    public void setTracking() {
        this.tracked = true;
    }

    public void untrack() {
        this.tracked = false;
    }

    public void setHasPopularGenotype() {
        this.hasPopularGenotype = true;
    }

    public void unsetHasPopularGenotype() {
        this.hasPopularGenotype = false;
    }

    public void setAnimalBehavior(IAnimalBehavior animalBehavior) {
        this.animalBehavior = animalBehavior;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public int getEnergy() {
        return this.energy;
    }

    public void setEnergy(int value) {
        this.energy = value;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int value) {
        this.age = value;
    }

    public int getChildCounter() {
        return this.childCounter;
    }

    public void setChildCounter(int value) {
        this.childCounter = value;
    }

    // generate genotype for first animals on map
    public void generateGenotype(int size) {
        for (int i = 0; i < size; i++) {
            this.genotype.add((int) (Math.random() * 8));
        }
    }

    // generate random position for first animals on map
    public void generatePosition(WorldMap map) {
        this.position = new Vector2d((int) (Math.random() * map.mapWidth), (int) (Math.random() * map.mapHeight));
    }

    // generate genotype for child of Animal mother and Animal father
    public void createGenotype(Animal mother, Animal father) {
        float suma = mother.getEnergy() + father.getEnergy();
        int lenOfStrongerGenes;
        Animal strongerParent, weakerParent;

        this.genotype = new ArrayList<>();

        // Select stronger parent
        if (mother.getEnergy() >= father.getEnergy()) {
            strongerParent = mother;
            weakerParent = father;
        } else {
            strongerParent = father;
            weakerParent = mother;
        }

        // Find how much genes stronger parent passes to child
        lenOfStrongerGenes = Math.round(((float) ((strongerParent.getEnergy()) / suma) * (float) (strongerParent.genotype.size())));

        // 0 - lenOfParentGenes right side strongerParents genes, 1 - left side genes
        int strongerGenotypeOnLeft = (int) (Math.random() * 2);

        // If 0 - take right side stronger parents genes and pass it to right side of child genotype,
        // If 1 - take left side stronger parents genes and pass it to left side of child genotype
        if (strongerGenotypeOnLeft == 0) {
            for (int x = 0; x < lenOfStrongerGenes; x++) {
                this.genotype.add(strongerParent.genotype.get(x));
            }
            for (int x = lenOfStrongerGenes; x < mother.genotype.size(); x++) {
                this.genotype.add(weakerParent.genotype.get(x));
            }
        } else {
            for (int x = 0; x < (mother.genotype.size() - lenOfStrongerGenes); x++) {
                this.genotype.add(weakerParent.genotype.get(x));
            }
            for (int x = (mother.genotype.size() - lenOfStrongerGenes); x < mother.genotype.size(); x++) {
                this.genotype.add(strongerParent.genotype.get(x));
            }
        }
        mother.childCounter += 1;
        father.childCounter += 1;
    }

    /**
     * Handle moving animal according to its genotype.
     * Getting gene depends on IAnimalBehavior.
     */
    public void move() {
        // Get index of gene that will move the animal (depends on IAnimalBehavior variable)
        this.idxOfGene = this.animalBehavior.getIdxOfGene(this, this.idxOfGene);

        // considering 0 - North, 1 - North-East, we get new Vector2d that we will add to current position.
        // we can get it by adding orientation numeric value and current gene and get modulo of 8
        int indexOfMove = (this.orientation.getNumericValue() + this.genotype.get(idxOfGene)) % 8;

        // change animal orientation and position in case of indexOfMove ( considering 0 - N, 1 - NE )
        Vector2d newVector = new Vector2d(0, 0);
        switch (indexOfMove) {
            case 0 -> {
                this.orientation = MoveDirection.N;
                newVector = new Vector2d(0, 1);
            }
            case 1 -> {
                this.orientation = MoveDirection.NE;
                newVector = new Vector2d(1, 1);
            }
            case 2 -> {
                this.orientation = MoveDirection.E;
                newVector = new Vector2d(1, 0);
            }
            case 3 -> {
                this.orientation = MoveDirection.SE;
                newVector = new Vector2d(1, -1);
            }
            case 4 -> {
                this.orientation = MoveDirection.S;
                newVector = new Vector2d(0, -1);
            }
            case 5 -> {
                this.orientation = MoveDirection.SW;
                newVector = new Vector2d(-1, -1);
            }
            case 6 -> {
                this.orientation = MoveDirection.W;
                newVector = new Vector2d(-1, 0);
            }
            case 7 -> {
                this.orientation = MoveDirection.NW;
                newVector = new Vector2d(-1, 1);
            }
        }
        this.energy -= 1;
        this.age += 1;
        this.idxOfGene += 1;
        this.position = this.position.add(newVector);
    }

    @Override
    public String toString() {
        return this.orientation.toString();
    }

    public Circle paintObject(int avgMapEnergy, int size) {
        if (this.tracked) {
            return new Circle(size, Color.rgb(79, 133, 217));
        }

        if (this.hasPopularGenotype) {
            return new Circle(size, Color.rgb(255, 222, 0));
        }

        if (this.getEnergy() >= 2 * avgMapEnergy) {
            return new Circle(size, Color.rgb(54, 0, 0));
        } else if (this.getEnergy() >= 1.5 * avgMapEnergy) {
            return new Circle(size, Color.rgb(117, 15, 8));
        } else if (this.getEnergy() >= 1.2 * avgMapEnergy) {
            return new Circle(size, Color.rgb(140, 12, 3));
        } else if (this.getEnergy() >= 0.8 * avgMapEnergy) {
            return new Circle(size, Color.rgb(219, 74, 64));
        } else if (this.getEnergy() >= 0.4 * avgMapEnergy) {
            return new Circle(size, Color.rgb(222, 108, 100));
        } else if (this.getEnergy() >= 0.2 * avgMapEnergy) {
            return new Circle(size, Color.rgb(237, 160, 154));
        } else {
            return new Circle(size, Color.rgb(247, 224, 223));
        }
    }
}
