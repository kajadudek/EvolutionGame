package simulation;

import elements.AnimalElement.CrazyBehavior;
import elements.AnimalElement.Predestination;
import elements.AnimalElement.RandomMutation;
import elements.AnimalElement.SmallMutation;
import interfaces.IAnimalBehavior;
import interfaces.IGenotypeMutation;
import interfaces.IMapType;
import interfaces.IPlantFields;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import maps.GlobeMap;
import maps.GreenBelt;
import maps.NetherMap;
import maps.ToxicFields;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;


public class SimulationVariables {
    public final IMapType mapType;
    public final IPlantFields plantFields;
    public final IAnimalBehavior animalBehavior;
    public final IGenotypeMutation genotypeMutation;
    public int mapHeight;
    public int mapWidth;
    public int copulationMinEnergy;
    public int copulationLossEnergy;
    public int grassPerDay, eatingEnergy;
    public int minMutations, maxMutations;
    public int animalsOnStart, startEnergy;
    public int genotypeSize;


    public SimulationVariables() throws IOException, IllegalArgumentException, ParseException {

        try (FileReader reader = new FileReader("src/main/resources/settings.json")) {
            Object obj = new JSONParser().parse(reader);
            JSONObject jsonObj = (JSONObject) obj;
            String temp;

            temp = ((String) jsonObj.get("mapType")).toString();
            if (temp.toLowerCase().equals("globemap")){
                this.mapType = new GlobeMap();
            } else { this.mapType = new NetherMap(); }

            temp = ((String) jsonObj.get("plantFields")).toString();
            if (temp.toLowerCase().equals("greenbelt")){
                this.plantFields = new GreenBelt();
            } else { this.plantFields = new ToxicFields(); }

            temp = ((String) jsonObj.get("animalBehavior")).toString();
            if (temp.toLowerCase().equals("predestination")){
                this.animalBehavior = new Predestination();
            } else { this.animalBehavior = new CrazyBehavior(); }

            temp = ((String) jsonObj.get("genotypeMutation")).toString();
            if (temp.toLowerCase().equals("randommutation")){
                this.genotypeMutation = new RandomMutation();
            } else { this.genotypeMutation = new SmallMutation(); }

            this.mapHeight = ((Number) jsonObj.get("mapHeight")).intValue();
            this.mapWidth = ((Number) jsonObj.get("mapWidth")).intValue();
            this.copulationMinEnergy = ((Number) jsonObj.get("copulationMinEnergy")).intValue();
            this.copulationLossEnergy = ((Number) jsonObj.get("copulationLossEnergy")).intValue();
            this.grassPerDay = ((Number) jsonObj.get("grassPerDay")).intValue();
            this.eatingEnergy = ((Number) jsonObj.get("eatingEnergy")).intValue();
            this.minMutations = ((Number) jsonObj.get("minMutations")).intValue();
            this.maxMutations = ((Number) jsonObj.get("maxMutations")).intValue();
            this.animalsOnStart = ((Number) jsonObj.get("animalsOnStart")).intValue();
            this.startEnergy = ((Number) jsonObj.get("startEnergy")).intValue();
            this.genotypeSize = ((Number) jsonObj.get("genotypeSize")).intValue();
        }
    }
}
