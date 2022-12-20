package maps;

import elements.Grass;
import elements.Vector2d;
import interfaces.IPlantFields;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.ceil;

public class GreenBelt implements IPlantFields {

    private int mapWidth, mapHeight;
    private int greenLowerY, greenUpperY;
    private List<Grass> grasses = new ArrayList<>();

    public void calculateGreenFields(WorldMap map) {
        int greenHeight = ((int)ceil(0.2 * (mapHeight+1) * (mapWidth+1))) / (mapWidth+1);

        // If greenHeight(mod mapWidth) >=5 then adding one moge greenBelt would be closer to 20% of the map
        if (((int)ceil(0.2 * (mapHeight+1) * (mapWidth+1))) % (mapWidth+1) >= 5) { greenHeight += 1;}

        if ((mapHeight + 1)%2 == 0){
            this.greenLowerY = (mapHeight + 1)/2 - greenHeight/2;
        }else {
            this.greenLowerY = (mapHeight)/2 - greenHeight/2;
        }
        this.greenUpperY = greenLowerY + greenHeight - 1;

        //Initialize empty fields on greenBelt
        for (int y = greenLowerY; y <= greenUpperY; y ++){
            for (int x = 0; x <= mapWidth; x++) {
                map.emptyGreenBeltField.add(new Vector2d(x,y));
            }
        }

        //Initialize empty fields outside greenBelt
        for (int y = 0; y <= mapHeight; y ++){
            if ((y >=0 && y < greenLowerY) || (y > greenUpperY && y <= mapHeight)){
                for (int x = 0; x <= mapWidth; x++) {
                    map.emptyNonGreenField.add(new Vector2d(x,y));
                }
            }
        }
    }

    @Override
    public void greenGrow(WorldMap map, int greenPerDay) {
        this.mapHeight = map.mapHeight;
        this.mapWidth = map.mapWidth;
        this.grasses = map.getGrasses();
    }
}
