package elements.AnimalElement;

import elements.Animal;
import interfaces.IGenotypeMutation;
import maps.WorldMap;

public class SmallMutation implements IGenotypeMutation {
    /**
     * Change howManyMutations genes in genotype to this.gene + 1 or this.gene -1
     * @param animal
     * @param map
     */
    @Override
    public void genotypeMutation(Animal animal, WorldMap map) {
        int minMutations = map.settings.minMutations;
        int maxMutations = map.settings.maxMutations;

        int howManyMutations = (int)(Math.random()*(maxMutations + 1 - minMutations) + minMutations);

        for (int i=0; i<howManyMutations; i++){
            int geneToMutate = (int)(Math.random()*animal.genotype.size());
            int downOrUp = (int)(Math.random()*2);
            if (downOrUp == 0) { downOrUp = -1; };
            int newGeneValue = animal.genotype.get(geneToMutate) + downOrUp;

            if (newGeneValue < 0) {
                newGeneValue = 7;
            }

            animal.genotype.set(geneToMutate, newGeneValue % 8);
        }
    }
}
