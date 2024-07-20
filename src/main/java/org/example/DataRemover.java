package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataRemover {
    public void removeHour(int hourId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM Hour WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, hourId);
                pstmt.executeUpdate();
            }
        }
    }
}
