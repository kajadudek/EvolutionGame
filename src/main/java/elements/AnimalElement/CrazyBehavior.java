package elements.AnimalElement;

import elements.Animal;
import interfaces.IAnimalBehavior;

public class CrazyBehavior implements IAnimalBehavior {

    /**
     * Handle one variant of animal behavior.
     * There 20% chance of choosing random gene that will move our animal
     * Otherwise, the next gene in queue will move it.
     * @param animal
     * @param idxOfGene
     * @return
     */
    @Override
    public int getIdxOfGene(Animal animal, int idxOfGene) {
        int random = (int)(Math.random()*10);

        if (random < 2) {
            return (int)(Math.random() * animal.genotype.size());
        } else {
            return idxOfGene % animal.genotype.size();
        }
    }
}
