package gui;

import backend.GraphCreator;
import backend.Record;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;
import java.util.ArrayList;

/**
 * Represents GraphTabController object that controls graphTab fxml
 * Holds variables and methods used in manipulation of graph tab object
 * @author Bede Skinner-Vennell
 * Date 09/10/2021
 */

public class GraphTabController {
    private final GraphCreator graphCreator = new GraphCreator();

    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();

    private final LineChart lineChart = new LineChart(xAxis, yAxis);

    @FXML
    private BorderPane graphBorderPane;

    /**
     * Disable graph animations to stop axis getting bunched up
     */
    @FXML
    private void initialize() {
        xAxis.setAnimated(false);
        yAxis.setAnimated(false);
        lineChart.setAnimated(false);
    }


    /**
     * Gets the graph time period and ArrayList of XYChart.Series containing the graph data from the graphCreator, then
     * sets the title and axis labels, updates the legend, clears the old data and displays the new data
     * @param currentRecords A list of the records currently displayed in the table
     * @param crimeTypes A list of crime types the user wants to graph
     */
    public void createCrimesPerTypeOverTimeGraph(ArrayList<Record> currentRecords, ArrayList<String> crimeTypes) {
        ArrayList<Record> filteredRecords = new ArrayList<>();
        for (Record record : currentRecords) {
            if (crimeTypes.contains(record.getPrimaryDescription())) {
                filteredRecords.add(record);
            }
        }
        ArrayList<Object> returnedInfo = graphCreator.createCrimesPerTypeOverTimeGraph(filteredRecords, crimeTypes);
        ArrayList<XYChart.Series> seriesList = (ArrayList<XYChart.Series>) returnedInfo.get(1);
        String label = (String) returnedInfo.get(0);

        lineChart.setTitle("Number of Crimes Per Type Over Time");
        xAxis.setLabel("Time (" + label + ")");
        yAxis.setLabel("Number of Crimes Per Type");

        lineChart.getData().clear();
        lineChart.setLegendVisible(true);

        for (String crimeType : crimeTypes) {
            lineChart.getData().add(seriesList.get(crimeTypes.indexOf(crimeType)));
        }

        graphBorderPane.setCenter(lineChart);
    }

    /**
     * Gets the graph time period and ArrayList of XYChart.Series containing the graph data from the graphCreator, then
     * sets the title and axis labels, updates the legend, clears the old data and displays the new data
     * @param currentRecords A list of the records currently displayed in the table
     * @param crimeBeats A list of the crime beats the user wants to graph
     */
    public void createCrimesPerBeatOverTimeGraph(ArrayList<Record> currentRecords, ArrayList<Integer> crimeBeats) {
        ArrayList<Record> filteredRecords = new ArrayList<>();
        for (Record record : currentRecords) {
            if (crimeBeats.contains(record.getBeat())) {
                filteredRecords.add(record);
            }
        }
        ArrayList<Object> returnedInfo = graphCreator.createCrimesPerBeatOverTimeGraph(filteredRecords, crimeBeats);
        ArrayList<XYChart.Series> seriesList = (ArrayList<XYChart.Series>) returnedInfo.get(1);
        String label = (String) returnedInfo.get(0);

        lineChart.setTitle("Number of Crimes Per Beat Over Time");
        xAxis.setLabel("Time (" + label + ")");
        yAxis.setLabel("Number of Crimes Per Beat");

        lineChart.getData().clear();
        lineChart.setLegendVisible(true);

        for (int beat : crimeBeats) {
            lineChart.getData().add(seriesList.get(crimeBeats.indexOf(beat)));
        }

        graphBorderPane.setCenter(lineChart);
    }

    /**
     * Gets the graph time period and ArrayList of XYChart.Series containing the graph data from the graphCreator, then
     * sets the title and axis labels, updates the legend, clears the old data and displays the new data
     * @param currentRecords A list of the records currently displayed in the table
     * @param crimeWards A list of the crime wards the user wants to graph
     */
    public void createCrimesPerWardOverTimeGraph(ArrayList<Record> currentRecords, ArrayList<Integer> crimeWards) {
        ArrayList<Record> filteredRecords = new ArrayList<>();
        for (Record record : currentRecords) {
            if (crimeWards.contains(record.getWard())) {
                filteredRecords.add(record);
            }
        }
        ArrayList<Object> returnedInfo = graphCreator.createCrimesPerWardOverTimeGraph(filteredRecords, crimeWards);
        ArrayList<XYChart.Series> seriesList = (ArrayList<XYChart.Series>) returnedInfo.get(1);
        String label = (String) returnedInfo.get(0);

        lineChart.setTitle("Number of Crimes Per Ward Over Time");
        xAxis.setLabel("Time (" + label + ")");
        yAxis.setLabel("Number of Crimes Per Ward");

        lineChart.getData().clear();
        lineChart.setLegendVisible(true);

        for (int ward : crimeWards) {
            lineChart.getData().add(seriesList.get(crimeWards.indexOf(ward)));
        }

        graphBorderPane.setCenter(lineChart);

    }

    /**
     * Gets the graph time period and XYChart.Series containing the graph data from the graphCreator, then sets
     * the title and axis labels, removes the legend, clears the old data and displays the new data
     * @param currentRecords A list of the records currently displayed in the table
     */
    public void createCrimesOverTimeGraph(ArrayList<Record> currentRecords) {
        ArrayList<Object> returnedInfo = graphCreator.createCrimesOverTimeGraph(currentRecords);

        XYChart.Series series = (XYChart.Series) returnedInfo.get(1);
        String label = (String) returnedInfo.get(0);

        lineChart.setTitle("Crimes Over Time");
        xAxis.setLabel("Time (" + label + ")");
        yAxis.setLabel("Number of Crimes");

        lineChart.setLegendVisible(false);
        lineChart.getData().clear();
        lineChart.getData().add(series);

        graphBorderPane.setCenter(lineChart);
    }
}
