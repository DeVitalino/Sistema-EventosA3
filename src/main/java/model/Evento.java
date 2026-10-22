package model;

import java.time.LocalDateTime;

public class Evento {

    private final int id;
    private final String nome;
    private final String endereco;
    private final String cidade;
    private final Categoria categoria;
    private final LocalDateTime horario;
    private final String descricao;
    private final int duracaoMinutos;

    public Evento(int id, String nome, String endereco, String cidade, Categoria categoria,
                  LocalDateTime horario, String descricao, int duracaoMinutos) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.cidade = cidade;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
        this.duracaoMinutos = duracaoMinutos;
    }

    public LocalDateTime getFim() {
        return horario.plusMinutes(duracaoMinutos);
    }

    public boolean estaAcontecendo() {
        LocalDateTime agora = LocalDateTime.now();
        return !agora.isBefore(horario) && !agora.isAfter(getFim());
    }

    public boolean jaTerminou() {
        return LocalDateTime.now().isAfter(getFim());
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }
}
