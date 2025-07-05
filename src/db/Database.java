package db;

import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:tarefas.db";

    static {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS tarefas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "descricao TEXT NOT NULL," +
                    "concluida BOOLEAN NOT NULL DEFAULT 0)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
