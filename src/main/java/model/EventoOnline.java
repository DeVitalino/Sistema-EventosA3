package model;

import java.time.LocalDateTime;

public class EventoOnline extends Evento {

    private final String plataforma;
    private final String link;

    public EventoOnline(int id, String nome, String plataforma, String link,
                        Categoria categoria, LocalDateTime horario,
                        String descricao, int duracaoMinutos) {
        super(id, nome, categoria, horario, descricao, duracaoMinutos);
        this.plataforma = plataforma;
        this.link = link;
    }

    @Override
    public String getTipo() {
        return "Online";
    }

    @Override
    public String getLocal() {
        return plataforma;
    }

    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Link: " + link);
    }

    public String getPlataforma() {
        return plataforma;
    }

    public String getLink() {
        return link;
    }
}
