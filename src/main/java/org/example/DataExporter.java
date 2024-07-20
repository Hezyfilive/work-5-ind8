package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataExporter {
    public static void exportToJson(String jsonFilePath) throws Exception {
        List<MetroStationArrayList> stations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String stationQuery = "SELECT id, name, year FROM MetroStationArrayList";
            String hourQuery = "SELECT passengerCount, comment, dateTime FROM Hour WHERE metroStationId = ?";

            try (PreparedStatement stationStmt = conn.prepareStatement(stationQuery);
                 PreparedStatement hourStmt = conn.prepareStatement(hourQuery);
                 ResultSet stationRs = stationStmt.executeQuery()) {

                while (stationRs.next()) {
                    int stationId = stationRs.getInt("id");
                    String name = stationRs.getString("name");
                    int year = stationRs.getInt("year");

                    MetroStationArrayList station = new MetroStationArrayList(name, year);

                    hourStmt.setInt(1, stationId);
                    try (ResultSet hourRs = hourStmt.executeQuery()) {
                        while (hourRs.next()) {
                            int passengerCount = hourRs.getInt("passengerCount");
                            String comment = hourRs.getString("comment");
                            Timestamp dateTime = hourRs.getTimestamp("dateTime");

                            Hour hour = new Hour(passengerCount, comment, dateTime.toLocalDateTime());
                            station.addHour(hour);
                        }
                    }

                    stations.add(station);
                }
            }
        }

        DataWrapper data = new DataWrapper();
        data.stations = stations;

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.writeValue(new File(jsonFilePath), data);
    }

    static class DataWrapper {
        public List<MetroStationArrayList> stations;
        public List<Hour> hours;

        public DataWrapper(List<MetroStationArrayList> stations, List<Hour> hours) {
            this.stations = stations;
            this.hours = hours;
        }

        public DataWrapper() {

        }
    }
}
