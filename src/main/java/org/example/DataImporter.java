package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.sql.*;

public class DataImporter {
    private static final String INSERT_STATION_SQL = "INSERT INTO MetroStationArrayList (name, year) VALUES (?, ?)";
    private static final String INSERT_HOUR_SQL = "INSERT INTO Hour (passengerCount, comment, dateTime, metroStationId) VALUES (?, ?, ?, ?)";

    public static void importFromJson(String jsonFilePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());  // Register the module
        DataExporter.DataWrapper data = mapper.readValue(new File(jsonFilePath), DataExporter.DataWrapper.class);

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Enable transaction

            try (PreparedStatement stationStmt = conn.prepareStatement(INSERT_STATION_SQL, new String[]{"id"});
                 PreparedStatement hourStmt = conn.prepareStatement(INSERT_HOUR_SQL)) {

                for (MetroStationArrayList station : data.stations) {
                    stationStmt.setString(1, station.getName());
                    stationStmt.setInt(2, station.getYear());
                    stationStmt.executeUpdate();

                    try (ResultSet generatedKeys = stationStmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int stationId = generatedKeys.getInt(1);

                            for (Hour hour : station.getSequence()) {
                                hourStmt.setInt(1, hour.getPassengerCount());
                                hourStmt.setString(2, hour.getComment());
                                hourStmt.setTimestamp(3, Timestamp.valueOf(hour.getDateTime()));
                                hourStmt.setInt(4, stationId);
                                hourStmt.executeUpdate();
                            }
                        }
                    }
                }

                conn.commit(); // Commit transaction
            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction on error
                throw e;
            }
        }
    }

}
