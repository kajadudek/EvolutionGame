package interfaces;

import elements.Animal;

public interface IAnimalBehavior {

    /**
     * Return index of gene that will perform the move.
     * @param animal
     * @return
     */
    int getIdxOfGene(Animal animal, int idxOfGene);
}
