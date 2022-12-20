package org.example;

import elements.Grass;
import elements.Vector2d;
import maps.GlobeMap;
import interfaces.IEngine;
import simulation.SimulationEngine;
import simulation.SimulationVariables;

public class Main {
    public static void main(String[] args) {
        SimulationVariables settings = new SimulationVariables(new GlobeMap(), new Grass(new Vector2d(1,1)),
                10, 10, 30, 15 );
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4), new Vector2d(2,2) };
        // Randomowe dane aby sprawdzic czy dziala
        IEngine engine = new SimulationEngine(settings, positions);
        engine.run();
    }
}