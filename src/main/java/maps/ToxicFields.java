package maps;

import elements.Animal;
import elements.Grass;
import elements.ToxicField;
import elements.Vector2d;
import interfaces.IPlantFields;

import java.util.*;
import java.util.stream.Stream;

public class ToxicFields implements IPlantFields {

    private int mapWidth, mapHeight;
    Map<Vector2d, Integer> deathCounter;

    @Override
    public void calculateGreenFields(WorldMap map) {
        this.mapHeight = map.mapHeight;
        this.mapWidth = map.mapWidth;
        this.deathCounter = map.deathCounter;

        //Initialize deathCounter for every field
        for (int y = 0; y <= mapHeight; y ++){
            for (int x = 0; x <= mapWidth; x++) {
                this.deathCounter.put(new Vector2d(x,y), 0);
            }
        }
    }

    // Creates priority queue from Map<Position, DeathCounter> with ToxicFields and counter of animals' deaths in it
    private PriorityQueue<ToxicField> getPriorityQueue(Map<Vector2d, Integer> deathCounter, WorldMap map){
        int sizeOfPQ = deathCounter.size();
        PriorityQueue<ToxicField> pq = new PriorityQueue<>(sizeOfPQ, new ToxicFieldComparator());

        for (Map.Entry<Vector2d, Integer> entry: deathCounter.entrySet()) {
            Vector2d key = entry.getKey();
            int val = entry.getValue();
            if (!map.isGrassThere(key)){
                pq.add(new ToxicField(key, val));
            }
        }
        return pq;
    }

    @Override
    public void greenGrow(WorldMap map, int greenPerDay) {
        this.mapHeight = map.mapHeight;
        this.mapWidth = map.mapWidth;
        List<Grass> grasses = map.getGrasses();
        PriorityQueue<ToxicField> pq = getPriorityQueue(map.deathCounter, map);

        // Iteration for every grass that will grow that day
        for (int i=0; i < greenPerDay; i++){
            if (pq.isEmpty()){ break; }
            List<ToxicField> whereToGrow = new ArrayList<>();

            // Get positions with the min deathCounter and pick one randomly.
            // Then add the rest of them to the priorityQueue
            whereToGrow.add(pq.poll());

            while (!pq.isEmpty()) {
                ToxicField temp = pq.poll();
                if (temp.deaths != whereToGrow.get(0).deaths){
                    break;
                }
                whereToGrow.add(temp);
            }

            // Pick random grass from whereToGrow list
            int randIdx = (int)(Math.random()*whereToGrow.size());
            grasses.add(new Grass(whereToGrow.remove(randIdx).position));

            // Add them back to PQ
            while (!whereToGrow.isEmpty()){
                pq.add(whereToGrow.remove(0));
            }
        }
    }
}
