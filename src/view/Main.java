package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe principal da aplicação de Checklist.
 * Permite adicionar tarefas, marcar como concluídas e excluir da lista.
 */
public class Main {

    /**
     * Método principal que inicializa e exibe a interface gráfica da aplicação.
     * @param args Argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        // Criação da janela principal
        JFrame frame = new JFrame("Checklist");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480, 500);

        // Painel principal com layout vertical
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // margem interna

        // Campo de texto para digitar nova tarefa
        JTextField taskField = new JTextField();
        taskField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(taskField);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // espaço vertical

        // Botão para adicionar nova tarefa
        JButton addButton = new JButton("Adicionar Tarefa");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(addButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Painel onde as tarefas serão exibidas
        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        tasksPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        // Scroll para exibir as tarefas
        JScrollPane scrollPane = new JScrollPane(tasksPanel);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        panel.add(scrollPane);

        /**
         * Função responsável por adicionar uma nova tarefa à lista.
         * Cria um painel com checkbox, label com texto, e botão de exclusão.
         */
        Runnable addTask = () -> {
            String taskText = taskField.getText().trim();

            // Ignora entradas vazias
            if (!taskText.isEmpty()) {

                // Painel de item de tarefa (horizontal)
                JPanel taskItemPanel = new JPanel();
                taskItemPanel.setLayout(new BoxLayout(taskItemPanel, BoxLayout.X_AXIS));
                taskItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                taskItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                taskItemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                // Checkbox para marcar como concluída
                JCheckBox checkBox = new JCheckBox();
                checkBox.setOpaque(false);

                // Label da tarefa com quebra de linha automática
                JLabel taskLabel = new JLabel("<html><div style='width:250px;'>" + taskText + "</div></html>");

                // Botão para excluir tarefa
                JButton deleteButton = new JButton("Excluir");
                deleteButton.setFocusPainted(false);
                deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Evento ao marcar/desmarcar a tarefa
                checkBox.addItemListener(e -> {
                    if (checkBox.isSelected()) {
                        // Risco no texto se concluída
                        taskLabel.setText("<html><div style='width:250px;'><strike>" + taskText + "</strike></div></html>");
                        // Move o item para o fim da lista
                        tasksPanel.remove(taskItemPanel);
                        tasksPanel.add(taskItemPanel);
                    } else {
                        // Remove o risco se desmarcada
                        taskLabel.setText("<html><div style='width:250px;'>" + taskText + "</div></html>");
                    }
                    tasksPanel.revalidate();
                    tasksPanel.repaint();
                });

                // Evento para excluir tarefa
                deleteButton.addActionListener(e -> {
                    tasksPanel.remove(taskItemPanel);
                    tasksPanel.revalidate();
                    tasksPanel.repaint();
                });

                // Montagem visual do item da tarefa
                taskItemPanel.add(checkBox);
                taskItemPanel.add(Box.createRigidArea(new Dimension(5, 0))); // espaço entre checkbox e texto
                taskItemPanel.add(taskLabel);
                taskItemPanel.add(Box.createHorizontalGlue()); // empurra o botão para a direita
                taskItemPanel.add(deleteButton);

                // Adiciona no topo da lista
                tasksPanel.add(taskItemPanel, 0);
                tasksPanel.revalidate();
                tasksPanel.repaint();

                // Limpa o campo de entrada e mantém o foco
                taskField.setText("");
                taskField.requestFocusInWindow();
            }
        };

        // Ação do botão de adicionar tarefa
        addButton.addActionListener(e -> addTask.run());

        // Ação de tecla ENTER no campo de texto
        taskField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addTask.run();
                }
            }
        });

        // Adiciona o painel à janela e exibe a interface
        frame.add(panel);
        frame.setVisible(true);

        // Garante que o campo de texto comece com o foco
        SwingUtilities.invokeLater(taskField::requestFocusInWindow);
    }
}
