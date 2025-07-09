package view;

import dao.TarefaDAO;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Tarefa;

import java.util.List;

public class Main extends Application {

    private VBox taskListContainer;
    private TarefaDAO dao = new TarefaDAO();
    private ComboBox<String> filtroCombo;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        TextField taskField = new TextField();
        taskField.setPromptText("Digite a tarefa");

        Button addButton = new Button("Adicionar");
        HBox inputBox = new HBox(10, taskField, addButton);

        filtroCombo = new ComboBox<>();
        filtroCombo.getItems().addAll("Todas", "Pendentes", "Concluídas");
        filtroCombo.setValue("Todas");

        taskListContainer = new VBox(10);
        ScrollPane scrollPane = new ScrollPane(taskListContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);

        VBox root = new VBox(15, inputBox, filtroCombo, scrollPane);
        root.setPadding(new Insets(20));

        addButton.setOnAction(e -> {
            String text = taskField.getText().trim();
            if (!text.isEmpty()) {
                dao.adicionar(text);
                taskField.clear();
                renderTasks();
            }
        });

        filtroCombo.setOnAction(e -> renderTasks());

        renderTasks();

        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Checklist");
        stage.setScene(scene);
        stage.show();
    }

    private void renderTasks() {
        taskListContainer.getChildren().clear();
        String filtro = filtroCombo.getValue();
        List<Tarefa> tarefas = dao.listar(filtro);

        for (Tarefa t : tarefas) {
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(t.concluida);

            String descricao = t.concluida ? t.descricao + " (concluída)" : t.descricao;
            Label label = new Label(descricao + "\n" + (t.dataAlteracao != null ? t.dataAlteracao : ""));
            label.setWrapText(true);

            Button deleteButton = new Button("Excluir");

            HBox taskBox = new HBox(10, checkBox, label, deleteButton);
            taskBox.setPadding(new Insets(5));
            taskBox.setHgrow(label, Priority.ALWAYS);
            taskBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: lightgray;");

            checkBox.setOnAction(e -> {
                dao.atualizarConclusao(t.id, checkBox.isSelected());
                renderTasks();
            });

            deleteButton.setOnAction(e -> {
                dao.remover(t.id);
                renderTasks();
            });

            taskListContainer.getChildren().add(taskBox);
        }
    }
}