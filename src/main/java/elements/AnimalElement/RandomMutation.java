package elements.AnimalElement;

import elements.Animal;
import interfaces.IGenotypeMutation;
import maps.WorldMap;

public class RandomMutation implements IGenotypeMutation {
    /**
     * Change howManyMutations genes in genotype to random value from (0, 7)
     * @param animal
     */
    @Override
    public void genotypeMutation(Animal animal, WorldMap map) {
        int minMutations = map.settings.minMutations;
        int maxMutations = map.settings.maxMutations;

        int howManyMutations = (int)(Math.random()*(maxMutations + 1 - minMutations) + minMutations);

        for (int i=0; i<howManyMutations; i++){
            int geneToMutate = (int)(Math.random()*animal.genotype.size());

            animal.genotype.set(geneToMutate, (int)(Math.random()*8));
        }
    }
}
