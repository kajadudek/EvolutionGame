package org.example;

import elements.AnimalElement.RandomMutation;
import elements.AnimalElement.SmallMutation;
import elements.Vector2d;
import elements.AnimalElement.Predestination;
import maps.GlobeMap;
import interfaces.IEngine;
import maps.GreenBelt;
import maps.NetherMap;
import maps.ToxicFields;
import simulation.SimulationEngine;
import simulation.SimulationVariables;

public class Main {
    public static void main(String[] args) {
        SimulationVariables settings = new SimulationVariables(new GlobeMap(), new GreenBelt(),
                new Predestination(), new RandomMutation(), 5, 7, 30,
                15, 1, 5,1, 1, 4,
                45, 8);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4)};
        // Randomowe dane aby sprawdzic czy dziala
        IEngine engine = new SimulationEngine(settings, positions);
        engine.run();
    }
}