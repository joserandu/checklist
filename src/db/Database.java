package db;

import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:tarefas.db";

    static {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS tarefas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "descricao TEXT NOT NULL," +
                    "concluida BOOLEAN NOT NULL DEFAULT 0," +
                    "data_alteracao TEXT)");

            // Garante que a coluna data_alteracao exista mesmo se a tabela já existia
            ResultSet rs = stmt.executeQuery("PRAGMA table_info(tarefas)");
            boolean temColuna = false;
            while (rs.next()) {
                if ("data_alteracao".equals(rs.getString("name"))) {
                    temColuna = true;
                    break;
                }
            }
            if (!temColuna) {
                stmt.execute("ALTER TABLE tarefas ADD COLUMN data_alteracao TEXT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}