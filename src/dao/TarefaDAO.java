package dao;

import db.Database;
import model.Tarefa;

import java.sql.*;
import java.util.*;

public class TarefaDAO {

    public void adicionar(String descricao) {
        String sql = "INSERT INTO tarefas(descricao, concluida, data_alteracao) VALUES(?, 0, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descricao);
            stmt.setString(2, java.time.LocalDateTime.now().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Tarefa> listar(String filtro) {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM tarefas";

        if ("Pendentes".equals(filtro)) {
            sql += " WHERE concluida = 0";
        } else if ("Concluídas".equals(filtro)) {
            sql += " WHERE concluida = 1";
        }

        sql += " ORDER BY concluida, id DESC";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tarefas.add(new Tarefa(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getBoolean("concluida"),
                        rs.getString("data_alteracao")
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
        String sql = "UPDATE tarefas SET concluida = ?, data_alteracao = ? WHERE id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, concluida);
            stmt.setString(2, java.time.LocalDateTime.now().toString());
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}