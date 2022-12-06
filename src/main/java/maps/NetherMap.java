package maps;

/**
 Map that enables animals to cross the border and loose part of its energy and appear on randomly generated place.
 **/
public class NetherMap extends AbstractWorldMap{
    protected NetherMap(int mapHeight, int mapWidth) {
        super(mapHeight, mapWidth);
    }

    //TODO - Decrease animal's energy, then generate new random place on map for animal and adjust its position.
}
