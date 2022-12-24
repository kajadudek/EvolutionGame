package org.example;

import interfaces.IEngine;
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