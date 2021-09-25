package backend;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class
DataAnalyser {

    ArrayList<Integer> crimeWards = new ArrayList<>();
    ArrayList<String> crimeTypes = new ArrayList<>();
    ArrayList<Integer> crimeBeats = new ArrayList<>();

    public DataAnalyser() {}

    /**
     * Calls the updateRecords method to initialise the lists of crime types, wards and beats
     * @param records a list of all the crime records currently shown in the table
     */
    public DataAnalyser(ArrayList<Record> records) {
        updateRecords(records);
    }

    /**
     * Adds all the crime types, wards and beats to the relevant arraylists and then sorts them
     * @param records a list of all the crime records currently shown in the table
     */
    public void updateRecords(ArrayList<Record> records) {
        crimeTypes.clear();
        crimeWards.clear();
        crimeBeats.clear();

        for (Record record : records) {
            if (!crimeWards.contains(record.getWard())) {
                crimeWards.add(record.getWard());
            }
            if (!crimeTypes.contains(record.getPrimaryDescription())) {
                crimeTypes.add(record.getPrimaryDescription());
            }
            if (!crimeBeats.contains(record.getBeat())) {
                crimeBeats.add(record.getBeat());
            }
        }
        Collections.sort(crimeTypes);
        Collections.sort(crimeWards);
        Collections.sort(crimeBeats);
    }

    /**
     * Calculates the absolute value of the time difference between the crimes
     * @param record1 the first crime record
     * @param record2 the second crime record
     * @return a duration object that holds the time difference between the crimes
     */
    public Duration calculateTimeDifference(Record record1, Record record2) {
        return Duration.between(record1.getDateAsObject(), record2.getDateAsObject()).abs();
    }

    /**
     * Calculates the absolute value of the time difference between the crimes
     * @param time1 the date and time of the first crime record
     * @param time2 the date and time of the second crime record
     * @return a duration object that holds the time difference between the crimes
     */
    public Duration calculateTimeDifference(LocalDateTime time1, LocalDateTime time2) {
        return Duration.between(time1, time2).abs();
    }

    /**
     * Uses the Haversine formula to calculate the difference between the two crimes in meters, allowing for the
     * curvature of the earth
     * @param record1 the first crime record
     * @param record2 the second crime record
     * @return an integer distance between the two crimes in meters, or -1 if either
     *         of the records are missing location data
     */
    public int calculateLocationDifferenceMeters(Record record1, Record record2) {
        if (record1.getLocation() == null || record2.getLocation() == null) {
            return -1;
        }

        double record1Lat = record1.getLatitude();
        double record1Long = record1.getLongitude();
        double record2Lat = record2.getLatitude();
        double record2Long = record2.getLongitude();

        double earthRadius = 6371;

        double latDiff = Math.toRadians(record2Lat - record1Lat);
        double lonDiff = Math.toRadians(record2Long - record1Long);

        record1Lat = Math.toRadians(record1Lat);
        record2Lat = Math.toRadians(record2Lat);

        double a = Math.pow(Math.sin(latDiff / 2), 2) +
                Math.pow(Math.sin(lonDiff / 2), 2) *
                        Math.cos(record1Lat) *
                        Math.cos(record2Lat);
        double result = 2 * earthRadius * Math.asin(Math.sqrt(a));

        // Convert to meters and round to the nearest integer
        return (int) Math.round(result * 1000.0);
    }

    /**
     * Takes a data column and returns list of data column items together with their appearance frequency
     * @param column represents the data column
     * @return List of TypeFrequency pair objects
     * @throws SQLException
     */
    public ArrayList<TypeFrequencyPair> getTypeFrequencyDescending(ArrayList<Object> column) {

        ArrayList<TypeFrequencyPair> res = new ArrayList<TypeFrequencyPair>();
        long frequency;
        HashSet types = new HashSet();
        types.addAll(column);
        Map<Object, Long> resultMap = new HashMap<>();
        column.forEach(e -> resultMap.merge(e, 1L, Long::sum));

        for (Object type : types) {
            TypeFrequencyPair pair = new TypeFrequencyPair();
            frequency = resultMap.get(type);
            // Add to list if items appears in list
            pair.setType((String) type);
            pair.setFrequency(frequency);
            res.add(pair);

        }
        return res;
    }



    public ArrayList<String> getCrimeTypes() {
        return crimeTypes;
    }

    public ArrayList<Integer> getCrimeBeats() {
        return crimeBeats;
    }

    public ArrayList<Integer> getCrimeWards() {
        return crimeWards;
    }

}
