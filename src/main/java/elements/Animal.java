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

    public void createGenotype(Animal mother, Animal father){
        float suma = mother.getEnergy()+father.getEnergy();
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
        int strongerGenotypeOnLeft = (int)(Math.random()*2);

        // If 0 - take right side stronger parents genes and pass it to right side of child genotype,
        // If 1 - take left side stronger parents genes and pass it to left side of child genotype
        if (strongerGenotypeOnLeft == 0){
            for (int x=0; x<lenOfStrongerGenes; x++){
                this.genotype.add(strongerParent.genotype.get(x));
            }
            for (int x=lenOfStrongerGenes; x<mother.genotype.size(); x++){
                this.genotype.add(weakerParent.genotype.get(x));
            }
        } else {
            for (int x=0; x<(mother.genotype.size() - lenOfStrongerGenes); x++){
                this.genotype.add(weakerParent.genotype.get(x));
            }
            for (int x=(mother.genotype.size() - lenOfStrongerGenes); x<mother.genotype.size(); x++){
                this.genotype.add(strongerParent.genotype.get(x));
            }
        }
        System.out.println(this.genotype);
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
