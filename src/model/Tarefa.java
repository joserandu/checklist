package model;

public class Tarefa {
    public int id;
    public String descricao;
    public boolean concluida;
    public String dataAlteracao;

    public Tarefa(int id, String descricao, boolean concluida, String dataAlteracao) {
        this.id = id;
        this.descricao = descricao;
        this.concluida = concluida;
        this.dataAlteracao = dataAlteracao;
    }
}