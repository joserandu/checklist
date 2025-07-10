package test;

import dao.TarefaDAO;
import model.Tarefa;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

public class TarefaDAOTest {

    @Test
    public void testeAdicionarTarefa() {
        TarefaDAO dao = new TarefaDAO();
        dao.adicionar("Tarefa teste adicionar");
        List<Tarefa> tarefas = dao.listar("Todas");
        assertTrue(tarefas.stream().anyMatch(t -> t.descricao.equals("Tarefa teste adicionar")));
    }

    @Test
    public void testeAtualizarConclusao() {
        TarefaDAO dao = new TarefaDAO();
        dao.adicionar("Tarefa teste atualizar");
        List<Tarefa> tarefas = dao.listar("Todas");
        Tarefa t = tarefas.get(0);
        dao.atualizarConclusao(t.id, true);
        List<Tarefa> concluidas = dao.listar("Concluídas");
        assertTrue(concluidas.stream().anyMatch(task -> task.id == t.id && task.concluida));
    }

    @Test
    public void testeRemoverTarefa() {
        TarefaDAO dao = new TarefaDAO();
        dao.adicionar("Tarefa teste remover");
        List<Tarefa> tarefasAntes = dao.listar("Todas");
        int tamanhoAntes = tarefasAntes.size();

        Tarefa t = tarefasAntes.get(0);
        dao.remover(t.id);

        List<Tarefa> tarefasDepois = dao.listar("Todas");
        assertEquals(tamanhoAntes - 1, tarefasDepois.size());
        assertFalse(tarefasDepois.stream().anyMatch(task -> task.id == t.id));
    }
}
