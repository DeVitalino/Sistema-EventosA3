package model;

import java.time.LocalDateTime;

public class EventoPresencial extends Evento {

    private final String endereco;
    private final String cidade;

    public EventoPresencial(int id, String nome, String endereco, String cidade,
                            Categoria categoria, LocalDateTime horario,
                            String descricao, int duracaoMinutos) {
        super(id, nome, categoria, horario, descricao, duracaoMinutos);
        this.endereco = endereco;
        this.cidade = cidade;
    }

    @Override
    public String getTipo() {
        return "Presencial";
    }

    @Override
    public String getLocal() {
        return endereco + " - " + cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCidade() {
        return cidade;
    }
}
