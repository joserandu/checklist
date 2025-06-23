package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChecklistApp {
    public static void main(String[] args) {
        // Criar a janela
        JFrame frame = new JFrame("Checklist");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Campo para nova tarefa
        JTextField taskField = new JTextField();
        panel.add(taskField);

        // Botão para adicionar
        JButton addButton = new JButton("Adicionar Tarefa");
        panel.add(addButton);

        // Área para as tarefas (checklist)
        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(tasksPanel);
        panel.add(scrollPane);

        // Ação do botão
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskText = taskField.getText();
                if (!taskText.isEmpty()) {
                    JCheckBox checkBox = new JCheckBox(taskText);
                    tasksPanel.add(checkBox);
                    tasksPanel.revalidate();
                    taskField.setText("");
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
