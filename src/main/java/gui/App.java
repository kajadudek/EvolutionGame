package gui;

import elements.Animal;
import elements.Grass;
import elements.Vector2d;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import maps.WorldMap;
import simulation.SimulationEngine;
import simulation.SimulationVariables;

import java.io.FileNotFoundException;
import java.util.List;


public class App extends Application {
    WorldMap map;
    SimulationVariables settings;
    Thread engineThread;
    GridPane grid = new GridPane();
    VBox statsPanel, buttonsBox;
    Button untrackButton;
    HBox panel, mapGrid;
    SimulationEngine engine;
    boolean trackingOn = false, showingPopularGenotype = false;
    Animal trackedAnimal;

    public void runApp(SimulationVariables settings) {
        this.settings = settings;
        this.engine = new SimulationEngine(settings, this);
        this.map = engine.map;
        engineThread = new Thread(engine);
        Stage stage = new Stage();
        stage.setTitle("EvolutionGame");
        start(stage);
        engineThread.start();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                if (!settings.fileToExportName.equals("")){
                    try {
                        map.toCSVWriter.writeToCsv(settings.fileToExportName);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                engineThread.interrupt();
            }
        });
    }

    @Override
    public void init() {}

    public void start(Stage primaryStage) {
        drawButtons();
        drawScene();
        drawStats();
        panel = new HBox(mapGrid, statsPanel);
        int fieldSize = 25;
        if (map.mapHeight > 34 || map.mapWidth > 45){
            fieldSize = 20;
        }
        Scene scene = new Scene(panel, Math.max(map.mapWidth * fieldSize + 400, 450), Math.max(map.mapHeight * fieldSize + 80, 300));
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void drawButtons() {
        buttonsBox = new VBox(10);

        untrackButton = new Button("UNTRACK");
        untrackButton.setPadding(new Insets(5));
        untrackButton.setPrefSize(100,30);

        untrackButton.setOnAction(event -> {
            this.trackedAnimal.untrack();
            this.trackingOn = false;
            animation();
        });

        Button popularGenotypeButton = new Button(" POPULAR\nGENOTYPE");
        popularGenotypeButton.setPrefSize(100,50);

        popularGenotypeButton.setOnAction(event -> {
            if (!showingPopularGenotype){
                for (Animal animal: map.getAnimals()){
                    for (List<Integer> genotype: map.getPopularGenotypes()){
                        if (animal.genotype.equals(genotype)){
                            animal.setHasPopularGenotype();
                        }
                    }
                }
                showingPopularGenotype = true;
                animation();
            } else {
                for (Animal animal: map.getAnimals()) {
                    animal.unsetHasPopularGenotype();
                }
                showingPopularGenotype = false;
                animation();
            }
        });

        Button pauseButton = new Button("PAUSE");
        pauseButton.setPrefSize(100,30);
        buttonsBox.getChildren().addAll(pauseButton, popularGenotypeButton,
                untrackButton);
        buttonsBox.setPadding(new Insets(10));
        buttonsBox.setPrefWidth(100);
        buttonsBox.setAlignment(Pos.CENTER);
        mapGrid = new HBox(grid, buttonsBox);

        pauseButton.setOnAction(event -> {
            if (!this.engine.isPaused()) {
                this.engine.setPause();
                pauseButton.setText("RESUME");
            } else {
                this.engine.resume();
                pauseButton.setText("PAUSE");
            }
        });
    }

    /**
     * Draw map and handle animal tracking
     */
    public void drawScene() {
        Label label = new Label();
        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 0, 10, 20));

        int upperX = map.mapWidth;
        int upperY = map.mapHeight;
        int fieldSize = 25;
        int animalSize = 10;
        int grassSize = 20;

        if (upperX > 45 || upperY > 34){
            animalSize = 8;
            fieldSize = 20;
            grassSize = 17;
        }

        //Columns label
        for (int i = 0; i <= upperX; ++i) {
            label = new Label(Integer.toString(i));
            grid.getColumnConstraints().add(new ColumnConstraints(fieldSize));
            GridPane.setHalignment(label, HPos.CENTER);
        }

        //Rows label
        for (int i = upperY; i >= 0; --i) {
            label = new Label(Integer.toString(i));
            grid.getRowConstraints().add(new RowConstraints(fieldSize));
            GridPane.setHalignment(label, HPos.CENTER);
        }

        //Place object on map
        for (int i = 0; i <= upperX; ++i) {
            for (int j = upperY; j >= 0; --j) {
                Vector2d pos = new Vector2d(i, j);

                if (map.isOccupied(pos)) {
                    var object = map.objectAt(pos);

                    if (object instanceof Animal) {
                        Circle animalImg = ((Animal) object).paintObject(map.avgAnimalsEnergy, animalSize);
                        grid.add(animalImg, i, upperY - j);
                        GridPane.setHalignment(animalImg, HPos.CENTER);

                        animalImg.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (trackingOn) {
                                    trackedAnimal.untrack();
                                    trackingOn = false;
                                }
                                ((Animal) object).setTracking();
                                trackedAnimal = (Animal) object;
                                trackingOn = true;
                                animation();
                            }
                        });
                    }

                    if (object instanceof Grass) {
                        Rectangle grassImg = ((Grass) object).paintObject(grassSize);
                        grid.add(grassImg, i, upperY - j);
                        GridPane.setHalignment(grassImg, HPos.CENTER);
                    }
                }
            }
        }

        if(trackingOn){
            untrackButton.setVisible(true);
        } else {
            untrackButton.setVisible(false);
        }
    }

    /**
     * Draw map statistics
     */
    public void drawStats() {
        Label animalsOnMapLabel = new Label();
        animalsOnMapLabel.setPrefSize(210, 40);
        animalsOnMapLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        animalsOnMapLabel.setText("Animals on map: " + map.animalsOnMap);

        Label grassesOnMapLabel = new Label();
        grassesOnMapLabel.setPrefSize(210, 40);
        grassesOnMapLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        grassesOnMapLabel.setText("Grasses on map: " + map.grassesOnMap);

        Label avgEnergyLabel = new Label();
        avgEnergyLabel.setPrefSize(210, 40);
        avgEnergyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        avgEnergyLabel.setText("Average energy on map: " + map.avgAnimalsEnergy);

        Label emptyFieldsLabel = new Label();
        emptyFieldsLabel.setPrefSize(210, 40);
        emptyFieldsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        emptyFieldsLabel.setText("Empty map fields: " + map.emptyFields);

        Label avgDeathAgeLabel = new Label();
        avgDeathAgeLabel.setPrefSize(210, 40);
        avgDeathAgeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        avgDeathAgeLabel.setText("Average death age: " + map.avgDeadAnimalsAge);

        this.statsPanel = new VBox(animalsOnMapLabel, grassesOnMapLabel,
                avgEnergyLabel, emptyFieldsLabel, avgDeathAgeLabel, drawGenotypesStats(),
                drawTrackedAnimalInfo());
        this.statsPanel.setAlignment(Pos.CENTER);
    }

    /**
     * Draw most popular genotypes
     */
    public VBox drawGenotypesStats(){
        List<List<Integer>> genotypes = map.getPopularGenotypes();
        VBox popularGenotypes = new VBox();

        for (List<Integer> genotype: genotypes){
            Label genotypeLabel = new Label(genotype + " " + map.genotypes.get(genotype));
            popularGenotypes.getChildren().add(genotypeLabel);
        }
        return popularGenotypes;
    }

    /**
     * Draw info about tracked Animal
     * @return
     */
    public VBox drawTrackedAnimalInfo() {
        if (!this.trackingOn) {
            Label label = new Label("CLICK ON ANIMAL");
            label.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            label.setTextFill(Color.rgb(134,64,64));
            VBox trackedAnimalInfo = new VBox(label);
            trackedAnimalInfo.setPadding(new Insets(30, 10, 30, 10));
            return trackedAnimalInfo;
        } else {
            Label energyLabel = new Label();
            energyLabel.setPrefSize(210, 40);
            energyLabel.setFont(Font.font("Arial", 15));
            energyLabel.setText("Animal energy: " + trackedAnimal.getEnergy());

            Label eatenGrassesLabel = new Label();
            eatenGrassesLabel.setPrefSize(210, 40);
            eatenGrassesLabel.setFont(Font.font("Arial", 15));
            eatenGrassesLabel.setText("Eaten grasses: " + trackedAnimal.grassesEaten);

            Label childLabel = new Label();
            childLabel.setPrefSize(210, 40);
            childLabel.setFont(Font.font("Arial", 15));
            childLabel.setText("Children: " + trackedAnimal.getChildCounter());

            Label ageLabel = new Label();
            ageLabel.setPrefSize(210, 40);
            ageLabel.setFont(Font.font("Arial", 15));
            ageLabel.setText("Age: " + trackedAnimal.getAge());
            if (trackedAnimal.getEnergy() <= 0) {
                ageLabel.setText("Day of death: " + trackedAnimal.getAge());
            }

            VBox trackedAnimalInfo = new VBox(energyLabel, eatenGrassesLabel, childLabel, ageLabel, drawGenotype());
            trackedAnimalInfo.setPrefHeight(200);
            trackedAnimalInfo.setAlignment(Pos.CENTER);

            return trackedAnimalInfo;
        }
    }

    /**
     * Draw genotype and its activated gene
     * @return
     */
    public HBox drawGenotype(){
        HBox genotype = new HBox();
        for (int i = 1; i <= settings.genotypeSize; i ++){
            Label gene = new Label(Integer.toString(trackedAnimal.genotype.get(i-1)));
            gene.setFont(Font.font("Arial", 15));
            if (trackedAnimal.idxOfGene == i){
                gene.setTextFill(Color.RED);
            } else {
                gene.setTextFill(Color.BLACK);
            }
            genotype.getChildren().add(gene);
        }
        genotype.setPrefSize(210,40);
        return genotype;
    }


    /**
     * Handle animating whole map
     */
    public void animation() {
        Platform.runLater(() -> {
            grid.getChildren().clear();
            grid.getRowConstraints().clear();
            grid.getColumnConstraints().clear();
            grid.setGridLinesVisible(false);
            statsPanel.getChildren().clear();

            this.drawScene();
            this.drawStats();
            this.panel.getChildren().add(statsPanel);
        });
    }
}