package elements;

import interfaces.IAnimalBehavior;
import simulation.SimulationEngine;
import simulation.SimulationVariables;

import java.util.ArrayList;
import java.util.List;

public class Animal {
    public Vector2d position;
    public int energy;
    private int idxOfGene;
    MoveDirection orientation;
    public List<Integer> genotype = new ArrayList<>();
    IAnimalBehavior animalBehavior;

    // First animals with random genotype
    public Animal(Vector2d initialPosition, int energy, List<Integer> genotype){
        this.energy = energy;
        this.position = initialPosition;
        this.genotype = genotype;
        this.orientation = MoveDirection.N;
    }

    // Animals that were born and inherit genotype from parents
    public Animal(Vector2d initialPosition, int energy){
        this.energy = energy;
        this.position = initialPosition;
        this.orientation = MoveDirection.N;
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

    /**
     * Handle moving animal according to its genotype. Classic version.
     */
    public void move(){
        // Get index of gene that will move the animal (depends on IAnimalBehavior variable)
        this.idxOfGene = this.animalBehavior.getIdxOfGene(this, this.idxOfGene);

        // considering 0 - North, 1 - North-East, we get new Vector2d that we will add to current position.
        // we can get it by adding orientation numeric value and current gene and get modulo of 8
        int indexOfMove = (this.orientation.getNumericValue() + this.genotype.get(idxOfGene)) % 8;

        // change animal orientation and position in case of indexOfMove ( considering 0 - N, 1 - NE )
        Vector2d newVector = new Vector2d(0,0);
        switch (indexOfMove) {
            case 0 -> {
                this.orientation = MoveDirection.N;
                newVector = new Vector2d(0,1);
            }
            case 1 -> {
                this.orientation = MoveDirection.NE;
                newVector = new Vector2d(1,1);
            }
            case 2 -> {
                this.orientation = MoveDirection.E;
                newVector = new Vector2d(1,0);
            }
            case 3 -> {
                this.orientation = MoveDirection.SE;
                newVector = new Vector2d(1,-1);
            }
            case 4 -> {
                this.orientation = MoveDirection.S;
                newVector = new Vector2d(0,-1);
            }
            case 5 -> {
                this.orientation = MoveDirection.SW;
                newVector = new Vector2d(-1,-1);
            }
            case 6 -> {
                this.orientation = MoveDirection.W;
                newVector = new Vector2d(-1,0);
            }
            case 7 -> {
                this.orientation = MoveDirection.NW;
                newVector = new Vector2d(-1,1);
            }
        }
        this.idxOfGene += 1;
        this.position = this.position.add(newVector);
    }

    @Override
    public String toString() {
        if (this.orientation.toString().length() == 1){
            return (this.orientation.toString() + " ");
        }
        return this.orientation.toString();
    }
}
