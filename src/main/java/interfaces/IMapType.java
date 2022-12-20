package interfaces;

import elements.Animal;
import maps.WorldMap;

import java.util.List;

public interface IMapType {
    /**
     * Move animal on map. Respect its boundaries and rules of crossing it.
     *
     */
    void animalMoveOnMap(WorldMap map);
}
