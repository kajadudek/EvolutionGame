package elements.AnimalElement;

import elements.Animal;
import interfaces.IAnimalBehavior;

public class Predestination implements IAnimalBehavior {
    /**
     * One of types of animal behavior.
     * Animal always pick the next gene, that will move our animal.
     * @param animal
     * @param idxOfGene
     * @return
     */
    @Override
    public int getIdxOfGene(Animal animal, int idxOfGene) {

        return idxOfGene % animal.genotype.size();
    }
}
