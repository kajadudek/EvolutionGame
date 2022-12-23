package simulation;

import interfaces.IAnimalBehavior;
import interfaces.IGenotypeMutation;
import interfaces.IMapType;
import interfaces.IPlantFields;

public class SimulationVariables {
    public final IMapType mapType;
    public final IPlantFields plantFields;
    public final IAnimalBehavior animalBehavior;
    public final IGenotypeMutation genotypeMutation;
    public int mapHeight;
    public int mapWidth;
    public int copulationMinEnergy;
    public int copulationLossEnergy;
    public int grassPerDay, eatingEnergy;
    public int minMutations, maxMutations;
    public int animalsOnStart, startEnergy;
    public int genotypeSize;


    public SimulationVariables(IMapType mapType, IPlantFields plantFields, IAnimalBehavior animalBehavior,
                               IGenotypeMutation genotypeMutation, int mapHeight, int mapWidth,
                               int copulationMinEnergy, int copulationLossEnergy, int grassPerDay, int eatingEnergy,
                               int minMutations, int maxMutations, int animalsOnStart, int startEnergy,
                               int genotypeSize) {
        this.mapType = mapType;
        this.plantFields = plantFields;
        this.animalBehavior = animalBehavior;
        this.genotypeMutation = genotypeMutation;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.copulationMinEnergy = copulationMinEnergy;
        this.copulationLossEnergy = copulationLossEnergy;
        this.grassPerDay = grassPerDay;
        this.eatingEnergy = eatingEnergy;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        this.animalsOnStart = animalsOnStart;
        this.startEnergy = startEnergy;
        this.genotypeSize = genotypeSize;
    }
}
