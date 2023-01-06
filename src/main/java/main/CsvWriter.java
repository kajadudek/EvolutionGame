package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.opencsv.CSVWriter;

import static org.junit.Assert.assertTrue;

public class CsvWriter {
    List<String[]> listOfRecords = new ArrayList<>();
    List<String[]> list = new ArrayList<>();
    static String[] header = {"Day","AnimalsOnMap", "GrassesOnMap", "EmptyFields", "MostPopularGenotype1",
            "HowManyAnimals", "MostPopularGenotype2", "HowManyAnimals", "AvgEnergy", "AvgAge"};

    public CsvWriter(){

    }
    public void writeToCsv(String fileName) throws FileNotFoundException {
        list.add(header);
        list.addAll(listOfRecords);

        File csvOutputFile = new File("src/main/resources/stats/" + fileName + ".csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            list.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }
        assertTrue(csvOutputFile.exists());
    }

    public void createCsvData(String[] mapStats) {
        listOfRecords.add(mapStats);
    }

    private String convertToCSV(String[] data) {
        return String.join(";", data);
    }
}
