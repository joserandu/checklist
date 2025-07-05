package model;

public class Tarefa {
    public int id;
    public String descricao;
    public boolean concluida;

    public Tarefa(int id, String descricao, boolean concluida) {
        this.id = id;
        this.descricao = descricao;
        this.concluida = concluida;
    }
}