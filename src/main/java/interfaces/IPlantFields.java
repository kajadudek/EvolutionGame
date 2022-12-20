package interfaces;

import maps.WorldMap;

public interface IPlantFields {

    public void calculateGreenFields(WorldMap map);

    public void greenGrow(WorldMap map, int greenPerDay);
}
