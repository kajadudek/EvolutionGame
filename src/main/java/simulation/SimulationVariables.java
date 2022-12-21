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
    public int grassPerDay;
    public int minMutations, maxMutations;


    public SimulationVariables(IMapType mapType, IPlantFields plantFields, IAnimalBehavior animalBehavior,
                               IGenotypeMutation genotypeMutation, int mapHeight, int mapWidth,
                               int copulationMinEnergy, int copulationLossEnergy, int grassPerDay,
                               int minMutations, int maxMutations) {
        this.mapType = mapType;
        this.plantFields = plantFields;
        this.animalBehavior = animalBehavior;
        this.genotypeMutation = genotypeMutation;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.copulationMinEnergy = copulationMinEnergy;
        this.copulationLossEnergy = copulationLossEnergy;
        this.grassPerDay = grassPerDay;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
    }
}
