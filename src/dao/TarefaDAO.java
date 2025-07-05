package dao;

import db.Database;
import model.Tarefa;

import java.sql.*;
import java.util.*;

public class TarefaDAO {
    public void adicionar(String descricao) {
        String sql = "INSERT INTO tarefas(descricao, concluida) VALUES(?, 0)";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricao);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM tarefas ORDER BY concluida, id DESC";
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tarefas.add(new Tarefa(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getBoolean("concluida")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarefas;
    }

    public void remover(int id) {
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM tarefas WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarConclusao(int id, boolean concluida) {
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement("UPDATE tarefas SET concluida = ? WHERE id = ?")) {
            stmt.setBoolean(1, concluida);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}