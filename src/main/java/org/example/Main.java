package org.example;

import elements.AnimalElement.CrazyBehavior;
import elements.Vector2d;
import elements.AnimalElement.Predestination;
import maps.GlobeMap;
import interfaces.IEngine;
import maps.ToxicFields;
import simulation.SimulationEngine;
import simulation.SimulationVariables;

public class Main {
    public static void main(String[] args) {
        SimulationVariables settings = new SimulationVariables(new GlobeMap(), new ToxicFields(),
                new CrazyBehavior(), 5, 5, 30, 15, 1 );
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4), new Vector2d(2,2) };
        // Randomowe dane aby sprawdzic czy dziala
        IEngine engine = new SimulationEngine(settings, positions);
        engine.run();
    }
}