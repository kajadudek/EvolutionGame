package elements.AnimalElement;

import elements.Animal;
import interfaces.IAnimalBehavior;

public class CrazyBehavior implements IAnimalBehavior {

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
