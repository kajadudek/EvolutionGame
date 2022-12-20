package maps;

import elements.Grass;
import elements.Vector2d;
import interfaces.IPlantFields;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.ceil;

public class GreenBelt implements IPlantFields {

    private int mapWidth, mapHeight;
    List<Vector2d> emptyGreenBeltField;
    List<Vector2d> emptyNonGreenField;

    /**
     * Calculates 20% of map and its boundaries for green belt
     */
    public void calculateGreenFields(WorldMap map) {
        this.mapHeight = map.mapHeight;
        this.mapWidth = map.mapWidth;
        this.emptyNonGreenField = map.emptyNonGreenField;
        this.emptyGreenBeltField = map.emptyGreenBeltField;

        int greenHeight = ((int)ceil(0.2 * (mapHeight+1) * (mapWidth+1))) / (mapWidth+1);

        // If greenHeight(mod mapWidth) >=5 then adding one more greenBelt would be closer to 20% of the map
        if (((int)ceil(0.2 * (mapHeight+1) * (mapWidth+1))) % (mapWidth+1) >= 5) { greenHeight += 1;}

        if ((mapHeight + 1)%2 == 0){
            map.greenLowerY = (mapHeight + 1)/2 - greenHeight/2;
        }else {
            map.greenLowerY = (mapHeight)/2 - greenHeight/2;
        }
        map.greenUpperY = map.greenLowerY + greenHeight - 1;

        //Initialize empty fields on greenBelt
        for (int y = map.greenLowerY; y <= map.greenUpperY; y ++){
            for (int x = 0; x <= mapWidth; x++) {
                this.emptyGreenBeltField.add(new Vector2d(x,y));
            }
        }

        //Initialize empty fields outside greenBelt
        for (int y = 0; y <= mapHeight; y ++){
            if ((y >=0 && y < map.greenLowerY) || (y > map.greenUpperY)){
                for (int x = 0; x <= mapWidth; x++) {
                    this.emptyNonGreenField.add(new Vector2d(x,y));
                }
            }
        }
    }

    @Override
    public void greenGrow(WorldMap map, int greenPerDay) {
        List<Grass> grasses = map.getGrasses();

        for (int x=0; x < greenPerDay; x++) {
            int randomField = (int)(Math.random()*10);
            // 20% chance to spawn Grass outside the green belt
            if (randomField < 2) {
                if (map.emptyNonGreenField.size() > 0){
                    Collections.shuffle(map.emptyNonGreenField);
                    grasses.add(new Grass(map.emptyNonGreenField.get(0)));
                    map.emptyNonGreenField.remove(0);
                } else {
                    if(map.emptyGreenBeltField.size() > 0){
                        Collections.shuffle(map.emptyGreenBeltField);
                        grasses.add(new Grass(map.emptyGreenBeltField.get(0)));
                        map.emptyGreenBeltField.remove(0);
                    }
                }
                // 80% chance to spawn Grass inside the green belt
            }else {
                if(map.emptyGreenBeltField.size() > 0){
                    Collections.shuffle(map.emptyGreenBeltField);
                    grasses.add(new Grass(map.emptyGreenBeltField.get(0)));
                    map.emptyGreenBeltField.remove(0);
                } else {
                    if (map.emptyNonGreenField.size() > 0) {
                        Collections.shuffle(map.emptyNonGreenField);
                        grasses.add(new Grass(map.emptyNonGreenField.get(0)));
                        map.emptyNonGreenField.remove(0);
                    }
                }
            }
        }
    }
}
