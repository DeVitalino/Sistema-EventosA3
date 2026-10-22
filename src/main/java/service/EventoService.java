package service;

import model.Categoria;
import model.Evento;
import model.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventoService {

    private static final String CIDADE = "Porto Alegre";

    private final List<Evento> eventos = new ArrayList<>();
    private int proximoId = 1;

    public Evento cadastrar(String nome, String endereco, Categoria categoria,
                            LocalDateTime horario, String descricao, int duracaoMinutos) {
        Evento evento = new Evento(proximoId++, nome, endereco, CIDADE, categoria,
                horario, descricao, duracaoMinutos);
        eventos.add(evento);
        return evento;
    }

    public List<Evento> listar() {
        return List.copyOf(eventos);
    }

    public List<Evento> buscarPorCategoria(Categoria categoria) {
        List<Evento> resultado = new ArrayList<>();
        for (Evento evento : eventos) {
            if (evento.getCategoria() == categoria) {
                resultado.add(evento);
            }
        }
        return resultado;
    }

    // eventos em andamento que interessam ao usuário
    public List<Evento> acontecendoAgora(Usuario usuario) {
        List<Evento> resultado = new ArrayList<>();
        for (Evento evento : eventos) {
            if (evento.estaAcontecendo() && usuario.temInteresse(evento.getCategoria())) {
                resultado.add(evento);
            }
        }
        return resultado;
    }
}
