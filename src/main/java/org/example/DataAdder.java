package org.example;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DataAdder {
    public void addHour(Hour hour, int metroStationId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Hour (metroStationId, passengerCount, comment, dateTime) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, metroStationId);
                pstmt.setInt(2, hour.getPassengerCount());
                pstmt.setString(3, hour.getComment());
                pstmt.setTimestamp(4, Timestamp.valueOf(hour.getDateTime()));
                pstmt.executeUpdate();
            }
        }
    }
}
