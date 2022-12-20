package simulation;

import interfaces.IMapType;
import interfaces.IPlantFields;

public class SimulationVariables {
    public final IMapType mapType;
    public final IPlantFields plantFields;
    public int mapHeight;
    public int mapWidth;
    public int copulationMinEnergy;
    public int copulationLossEnergy;
    public int grassPerDay;


    public SimulationVariables(IMapType mapType, IPlantFields plantFields, int mapHeight, int mapWidth, int copulationMinEnergy, int copulationLossEnergy, int grassPerDay) {
        this.mapType = mapType;
        this.plantFields = plantFields;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.copulationMinEnergy = copulationMinEnergy;
        this.copulationLossEnergy = copulationLossEnergy;
        this.grassPerDay = grassPerDay;
    }
}
