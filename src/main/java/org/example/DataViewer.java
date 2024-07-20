package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataViewer {
    public void displayData() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM MetroStationArrayList");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Year: " + rs.getInt("year"));
            }

            rs = stmt.executeQuery("SELECT * FROM Hour");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Passenger Count: " + rs.getInt("passengerCount") + ", Comment: " + rs.getString("comment") + ", DateTime: " + rs.getTimestamp("dateTime"));
            }
        }
    }

}
