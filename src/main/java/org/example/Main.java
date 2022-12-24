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
import org.json.simple.parser.ParseException;
import simulation.SimulationEngine;
import simulation.SimulationVariables;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        SimulationVariables settings = new SimulationVariables();
        IEngine engine = new SimulationEngine(settings);
        engine.run();
    }
}