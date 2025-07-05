package view;

import dao.TarefaDAO;
import model.Tarefa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Checklist");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField taskField = new JTextField();
        taskField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(taskField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton addButton = new JButton("Adicionar Tarefa");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(addButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        tasksPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(tasksPanel);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        panel.add(scrollPane);

        TarefaDAO dao = new TarefaDAO();

        final Runnable[] renderTasks = new Runnable[1];

        renderTasks[0] = () -> {
            tasksPanel.removeAll();
            List<Tarefa> tarefas = dao.listar();
            for (Tarefa t : tarefas) {
                JPanel taskItemPanel = new JPanel();
                taskItemPanel.setLayout(new BoxLayout(taskItemPanel, BoxLayout.X_AXIS));
                taskItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                taskItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                taskItemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                JCheckBox checkBox = new JCheckBox();
                checkBox.setOpaque(false);
                checkBox.setSelected(t.concluida);

                JLabel taskLabel = new JLabel("<html><div style='width:250px;'>" +
                        (t.concluida ? "<strike>" + t.descricao + "</strike>" : t.descricao) +
                        "</div></html>");

                JButton deleteButton = new JButton("Excluir");
                deleteButton.setFocusPainted(false);
                deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                checkBox.addItemListener(e -> {
                    dao.atualizarConclusao(t.id, checkBox.isSelected());
                    renderTasks[0].run();
                });

                deleteButton.addActionListener(e -> {
                    dao.remover(t.id);
                    renderTasks[0].run();
                });

                taskItemPanel.add(checkBox);
                taskItemPanel.add(Box.createRigidArea(new Dimension(5, 0)));
                taskItemPanel.add(taskLabel);
                taskItemPanel.add(Box.createHorizontalGlue());
                taskItemPanel.add(deleteButton);

                tasksPanel.add(taskItemPanel);
            }
            tasksPanel.revalidate();
            tasksPanel.repaint();
        };

        addButton.addActionListener(e -> {
            String taskText = taskField.getText().trim();
            if (!taskText.isEmpty()) {
                dao.adicionar(taskText);
                taskField.setText("");
                renderTasks[0].run();
            }
        });

        taskField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addButton.doClick();
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);

        SwingUtilities.invokeLater(() -> {
            taskField.requestFocusInWindow();
            renderTasks[0].run();
        });
    }
}