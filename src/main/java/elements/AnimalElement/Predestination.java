package elements.AnimalElement;

import elements.Animal;
import interfaces.IAnimalBehavior;

public class Predestination implements IAnimalBehavior {
    @Override
    public int getIdxOfGene(Animal animal, int idxOfGene) {

        return idxOfGene % animal.genotype.size();
    }
}
