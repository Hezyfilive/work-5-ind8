package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataSorter {
    public List<Hour> sortHoursByPassengerCount() throws SQLException {
        List<Hour> hours = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Hour ORDER BY passengerCount DESC";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    hours.add(new Hour(
                            rs.getInt("passengerCount"),
                            rs.getString("comment"),
                            rs.getTimestamp("dateTime").toLocalDateTime()
                    ));
                }
            }
        }
        return hours;
    }

    public List<Hour> sortHoursByCommentLength() throws SQLException {
        List<Hour> hours = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Hour ORDER BY LENGTH(comment)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    hours.add(new Hour(
                            rs.getInt("passengerCount"),
                            rs.getString("comment"),
                            rs.getTimestamp("dateTime").toLocalDateTime()
                    ));
                }
            }
        }
        return hours;
    }

}