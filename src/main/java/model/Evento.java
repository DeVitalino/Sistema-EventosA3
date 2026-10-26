package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Evento {

    public static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final int id;
    private final String nome;
    private final Categoria categoria;
    private final LocalDateTime horario;
    private final String descricao;
    private final int duracaoMinutos;

    protected Evento(int id, String nome, Categoria categoria, LocalDateTime horario,
                     String descricao, int duracaoMinutos) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
        this.duracaoMinutos = duracaoMinutos;
    }

    public abstract String getTipo();

    public abstract String getLocal();

    public void exibirDetalhes() {
        System.out.println("\nID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Status: " + getStatus());
        System.out.println("Tipo: " + getTipo());
        System.out.println("Local: " + getLocal());
        System.out.println("Categoria: " + categoria.getDescricao());
        System.out.println("Horário: " + horario.format(FORMATO_DATA));
        System.out.println("Duração: " + duracaoMinutos + " minutos");
        System.out.println("Descrição: " + descricao);
    }

    public String getStatus() {
        if (estaAcontecendo()) {
            return "acontecendo agora";
        }
        if (jaTerminou()) {
            return "encerrado";
        }
        return "ainda vai acontecer";
    }

    public LocalDateTime getFim() {
        return horario.plusMinutes(duracaoMinutos);
    }

    public boolean aindaVaiAcontecer() {
        return LocalDateTime.now().isBefore(horario);
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
