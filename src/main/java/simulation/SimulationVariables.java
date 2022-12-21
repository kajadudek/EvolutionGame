package simulation;

import interfaces.IAnimalBehavior;
import interfaces.IMapType;
import interfaces.IPlantFields;

public class SimulationVariables {
    public final IMapType mapType;
    public final IPlantFields plantFields;
    public final IAnimalBehavior animalBehavior;
//    public final IGenotypeMutation genotypeMutation;
    public int mapHeight;
    public int mapWidth;
    public int copulationMinEnergy;
    public int copulationLossEnergy;
    public int grassPerDay;


    public SimulationVariables(IMapType mapType, IPlantFields plantFields, IAnimalBehavior animalBehavior, int mapHeight, int mapWidth, int copulationMinEnergy, int copulationLossEnergy, int grassPerDay) {
        this.mapType = mapType;
        this.plantFields = plantFields;
        this.animalBehavior = animalBehavior;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.copulationMinEnergy = copulationMinEnergy;
        this.copulationLossEnergy = copulationLossEnergy;
        this.grassPerDay = grassPerDay;
    }
}
