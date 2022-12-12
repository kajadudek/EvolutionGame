package org.example;

import elements.Animal;
import elements.Vector2d;
import maps.AbstractWorldMap;
import maps.GlobeMap;
import maps.NetherMap;
import simulation.IEngine;
import simulation.SimulationEngine;

public class Main {
    public static void main(String[] args) {
        AbstractWorldMap map = new NetherMap(4,3);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4), new Vector2d(2,2) };
        // Randomowe dane aby sprawdzic czy dziala
        IEngine engine = new SimulationEngine(map, positions, 30, 15, 1);
        engine.run();
        System.out.println(map);
    }
}