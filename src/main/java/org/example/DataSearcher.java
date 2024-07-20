package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DataSearcher {

    public List<Hour> searchComments(String regex) throws SQLException {
        List<Hour> hours = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Hour WHERE comment REGEXP ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, regex);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    hours.add(new Hour(rs.getInt("passengerCount"), rs.getString("comment"), rs.getTimestamp("dateTime").toLocalDateTime()));
                }
            }
        }
        return hours;
    }

    public List<Hour> findHoursWithMinPassengers() throws SQLException {
        List<Hour> hours = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Hour WHERE passengerCount = (SELECT MIN(passengerCount) FROM Hour)";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    hours.add(new Hour(rs.getInt("passengerCount"), rs.getString("comment"), rs.getTimestamp("dateTime").toLocalDateTime()));
                }
            }
        }
        return hours;
    }
}
