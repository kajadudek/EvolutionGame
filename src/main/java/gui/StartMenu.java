package gui;

import elements.AnimalElement.CrazyBehavior;
import elements.AnimalElement.Predestination;
import elements.AnimalElement.RandomMutation;
import elements.AnimalElement.SmallMutation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import maps.GlobeMap;
import maps.GreenBelt;
import maps.NetherMap;
import maps.ToxicFields;
import org.json.simple.parser.ParseException;
import simulation.SimulationVariables;

import java.io.IOException;

public class StartMenu extends Application {
    private final int menuHeight = 600;
    private boolean exportToFile = false;
    private final int menuWidth = 1000;

    private SimulationVariables settings;
    {
        try {
            settings = new SimulationVariables("settings.json");
        } catch (IllegalArgumentException | IOException | ParseException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Start");
        primaryStage.setMinWidth(menuWidth);
        primaryStage.setMinHeight(menuHeight);

        GridPane grid = draw();
        drawSettingsLabels(grid);
        Scene scene = new Scene(grid, menuWidth, menuHeight);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    private GridPane draw() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(5);
        grid.setVgap(10);

        // Constraints for ceratin col
        ColumnConstraints columnOneConstraints = new ColumnConstraints(150, 150, menuWidth);
        columnOneConstraints.setHalignment(HPos.RIGHT);

        ColumnConstraints columnTwoConstrains = new ColumnConstraints(300, 300, menuWidth);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);

        ColumnConstraints columnThreeConstraints = new ColumnConstraints(150, 150, menuWidth);
        columnThreeConstraints.setHalignment(HPos.RIGHT);

        ColumnConstraints columnFourConstraints = new ColumnConstraints(300, 300, menuWidth);
        columnFourConstraints.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains, columnThreeConstraints, columnFourConstraints);

        return grid;
    }

    private void drawSettingsLabels(GridPane grid) {
        Label headerLabel = new Label("SETTINGS");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        grid.add(headerLabel, 0, 0, 4, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        // Add Map Type Label
        Label mapTypeLabel = new Label("Map type: ");
        grid.add(mapTypeLabel, 0, 1);
        TextField mapType = new TextField();
        mapType.setPrefHeight(40);
        mapType.setPromptText("0 - GlobeMap, 1 - NetherMap; Default: 0");
        grid.add(mapType, 1, 1);

        // Add Plant Fields Label
        Label plantFieldsLabel = new Label("Plant fields: ");
        grid.add(plantFieldsLabel, 0, 2);
        TextField plantFields = new TextField();
        plantFields.setPrefHeight(40);
        plantFields.setPromptText("0 - GreenBelt, 1 - ToxicFields; Default: 0");
        grid.add(plantFields, 1, 2);

        // Add Animal Behavior Label
        Label animalBehaviorLabel = new Label("Animal behavior: ");
        grid.add(animalBehaviorLabel, 0, 3);
        TextField animalBehavior = new TextField();
        animalBehavior.setPrefHeight(40);
        animalBehavior.setPromptText("0 - Predestination, 1 - CrazyBehavior; Default: 0");
        grid.add(animalBehavior, 1, 3);

        // Add Genotype Mutation Label
        Label genotypeMutationLabel = new Label("Genotype mutation: ");
        grid.add(genotypeMutationLabel, 0, 4);
        TextField genotypeMutation = new TextField();
        genotypeMutation.setPrefHeight(40);
        genotypeMutation.setPromptText("0 - Random Mutation, 1 - Small Mutation; Default: 0");
        grid.add(genotypeMutation, 1, 4);

        // Add Height Label
        Label heightLabel = new Label("Map height: ");
        grid.add(heightLabel, 0, 5);
        TextField height = new TextField();
        height.setPrefHeight(40);
        height.setPromptText("Default: 20");
        grid.add(height, 1, 5);

        // Add Width Label
        Label widthLabel = new Label("Map width: ");
        grid.add(widthLabel, 0, 6);
        TextField width = new TextField();
        width.setPrefHeight(40);
        width.setPromptText("Default: 20");
        grid.add(width, 1, 6);

        // Add Minimal Copulation Energy Label
        Label minimalCopulationEnergyLabel = new Label("Minimal copulation energy: ");
        grid.add(minimalCopulationEnergyLabel, 0, 7);
        TextField minimalCopulationEnergy = new TextField();
        minimalCopulationEnergy.setPrefHeight(40);
        minimalCopulationEnergy.setPromptText("Default: 30");
        grid.add(minimalCopulationEnergy, 1, 7);

        // Add Copulation Loss Energy Label
        Label copulationLossEnergyLabel = new Label("Copulation loss energy: ");
        grid.add(copulationLossEnergyLabel, 0, 8);
        TextField copulationLossEnergy = new TextField();
        copulationLossEnergy.setPrefHeight(40);
        copulationLossEnergy.setPromptText("Default: 15");
        grid.add(copulationLossEnergy, 1, 8);

        // Add Grass Per Day Label
        Label grassPerDayLabel = new Label("Grass per day: ");
        grid.add(grassPerDayLabel, 2, 1);
        TextField grassPerDay = new TextField();
        grassPerDay.setPrefHeight(40);
        grassPerDay.setPromptText("Default: 2");
        grid.add(grassPerDay, 3, 1);

        // Add Eating Energy Label
        Label eatingEnergyLabel = new Label("Eating energy: ");
        grid.add(eatingEnergyLabel, 2, 2);
        TextField eatingEnergy = new TextField();
        eatingEnergy.setPrefHeight(40);
        eatingEnergy.setPromptText("Default: 5");
        grid.add(eatingEnergy, 3, 2);

        // Add Minimal Mutations Label
        Label minMutationsLabel = new Label("Minimal mutations: ");
        grid.add(minMutationsLabel, 2, 3);
        TextField minimalMutations = new TextField();
        minimalMutations.setPrefHeight(40);
        minimalMutations.setPromptText("Default: 1");
        grid.add(minimalMutations, 3, 3);

        // Add Maximal Mutations Label
        Label maxMutationsLabel = new Label("Maximal mutations: ");
        grid.add(maxMutationsLabel, 2, 4);
        TextField maximalMutations = new TextField();
        maximalMutations.setPrefHeight(40);
        maximalMutations.setPromptText("Default: 1");
        grid.add(maximalMutations, 3, 4);

        // Add Animals On Start Label
        Label animalsOnStartLabel = new Label("Animals on start: ");
        grid.add(animalsOnStartLabel, 2, 5);
        TextField animalsOnStart = new TextField();
        animalsOnStart.setPrefHeight(40);
        animalsOnStart.setPromptText("Default: 10");
        grid.add(animalsOnStart, 3, 5);

        // Add Start Energy Label
        Label startEnergyLabel = new Label("Start energy: ");
        grid.add(startEnergyLabel, 2, 6);
        TextField startEnergy = new TextField();
        startEnergy.setPrefHeight(40);
        startEnergy.setPromptText("Default: 100");
        grid.add(startEnergy, 3, 6);

        // Add Genotype Size Label
        Label genotypeSizeLabel = new Label("Genotype size: ");
        grid.add(genotypeSizeLabel, 2, 7);
        TextField genotypeSize = new TextField();
        genotypeSize.setPrefHeight(40);
        genotypeSize.setPromptText("Default: 10");
        grid.add(genotypeSize, 3, 7);

        // Add Configurations Select List
        Label configurationLabel = new Label("Or select configuration: ");
        grid.add(configurationLabel, 2, 8);
        String[] configurations = {"Config 1", "Config 2"};
        ComboBox dropdownList = new ComboBox(FXCollections
                .observableArrayList(configurations));
        dropdownList.setPrefHeight(40);
        dropdownList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (dropdownList.getValue().toString().equals("Config 2")) {
                    settings.getSettings2("settings2.json");
                } else {
                    settings.getSettings2("settings.json");
                }
            }
        });
        grid.add(dropdownList, 3, 8);

        // Add CSV File Label
        CheckBox exportCheckbox = new CheckBox("Want to export? Set file name: ");
        grid.add(exportCheckbox, 1, 9);
        GridPane.setMargin(exportCheckbox, new Insets(20,130,0,0));
        TextField export = new TextField();
        export.setPrefHeight(40);
        GridPane.setMargin(export, new Insets(20,130,0,0));
        export.setPromptText("Filename");
        export.setDisable(true);
        grid.add(export, 2, 9, 2, 1);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (exportCheckbox.isSelected()){
                    export.setDisable(false);
                    exportToFile = true;
                }
                else {
                    export.setDisable(true);
                    exportToFile = false;
                }
            }
        };
        exportCheckbox.setOnAction(event);

        // Add Start Button
        Button startButton = new Button("START SIMULATION");
        startButton.setFont(new Font("Arial", 20));
        startButton.setPrefHeight(40);
        startButton.setPrefWidth(500);
        grid.add(startButton, 0, 10, 4, 1);
        GridPane.setHalignment(startButton, HPos.CENTER);
        GridPane.setMargin(startButton, new Insets(10, 0, 20, 0));

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (mapType.getText() != null && !(mapType.getText().trim().isEmpty())) {
                    if (Integer.parseInt((mapType.getText())) == 0) {
                        settings.mapType = new GlobeMap();
                    } else if (Integer.parseInt((mapType.getText())) == 1) {
                        settings.mapType = new NetherMap();
                    } else {
                        throw new IllegalArgumentException("Map Type argument should be 0 or 1!");
                    }
                }

                if (plantFields.getText() != null && !(plantFields.getText().trim().isEmpty())) {
                    if (Integer.parseInt((plantFields.getText())) == 0) {
                        settings.plantFields = new GreenBelt();
                    } else if (Integer.parseInt((plantFields.getText())) == 1) {
                        settings.plantFields = new ToxicFields();
                    } else {
                        throw new IllegalArgumentException("Plant Fields argument should be 0 or 1!");
                    }
                }

                if (animalBehavior.getText() != null && !(animalBehavior.getText().trim().isEmpty())) {
                    if (Integer.parseInt((animalBehavior.getText())) == 0) {
                        settings.animalBehavior = new Predestination();
                    } else if (Integer.parseInt((animalBehavior.getText())) == 1) {
                        settings.animalBehavior = new CrazyBehavior();
                    } else {
                        throw new IllegalArgumentException("Animal Behavior argument should be 0 or 1!");
                    }
                }

                if (genotypeMutation.getText() != null && !(genotypeMutation.getText().trim().isEmpty())) {
                    if (Integer.parseInt((genotypeMutation.getText())) == 0) {
                        settings.genotypeMutation = new RandomMutation();
                    } else if (Integer.parseInt((genotypeMutation.getText())) == 1) {
                        settings.genotypeMutation = new SmallMutation();
                    } else {
                        throw new IllegalArgumentException("Genotype Mutation argument should be 0 or 1!");
                    }
                }

                if (height.getText() != null && !(height.getText().trim().isEmpty())) {
                    settings.mapHeight = Integer.parseInt((height.getText()));
                }

                if (width.getText() != null && !(width.getText().trim().isEmpty())) {
                    settings.mapWidth = Integer.parseInt((width.getText()));
                }

                if (minimalCopulationEnergy.getText() != null && !(minimalCopulationEnergy.getText().trim().isEmpty())) {
                    settings.copulationMinEnergy = Integer.parseInt((minimalCopulationEnergy.getText()));
                }

                if (copulationLossEnergy.getText() != null && !(copulationLossEnergy.getText().trim().isEmpty())) {
                    settings.copulationLossEnergy = Integer.parseInt((copulationLossEnergy.getText()));
                }

                if (grassPerDay.getText() != null && !(grassPerDay.getText().trim().isEmpty())) {
                    settings.grassPerDay = Integer.parseInt((grassPerDay.getText()));
                }

                if (eatingEnergy.getText() != null && !(eatingEnergy.getText().trim().isEmpty())) {
                    settings.eatingEnergy = Integer.parseInt((eatingEnergy.getText()));
                }

                if (minimalMutations.getText() != null && !(minimalMutations.getText().trim().isEmpty())) {
                    settings.minMutations = Integer.parseInt((minimalMutations.getText()));
                }

                if (maximalMutations.getText() != null && !(maximalMutations.getText().trim().isEmpty())) {
                    settings.maxMutations = Integer.parseInt((maximalMutations.getText()));
                }

                if (animalsOnStart.getText() != null && !(animalsOnStart.getText().trim().isEmpty())) {
                    settings.animalsOnStart = Integer.parseInt((animalsOnStart.getText()));
                }

                if (startEnergy.getText() != null && !(startEnergy.getText().trim().isEmpty())) {
                    settings.startEnergy = Integer.parseInt((startEnergy.getText()));
                }

                if (genotypeSize.getText() != null && !(genotypeSize.getText().trim().isEmpty())) {
                    settings.genotypeSize = Integer.parseInt((genotypeSize.getText()));
                }

                if (exportToFile && export.getText() != null && !(export.getText().trim().isEmpty())) {
                    settings.fileToExportName = export.getText();
                }

                settings.validateSettings();
                new App().runApp(settings);
            }
        });
    }
}

