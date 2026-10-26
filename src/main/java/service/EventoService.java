package service;

import model.Categoria;
import model.Evento;
import model.EventoOnline;
import model.EventoPresencial;
import model.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EventoService {

    private static final String CIDADE = "Porto Alegre";

    private static final Comparator<Evento> POR_HORARIO = Comparator.comparing(Evento::getHorario);

    private final List<Evento> eventos = new ArrayList<>();
    private int proximoId = 1;

    public Evento cadastrarPresencial(String nome, String endereco, Categoria categoria,
                                      LocalDateTime horario, String descricao, int duracaoMinutos) {
        Evento evento = new EventoPresencial(proximoId++, nome, endereco, CIDADE, categoria,
                horario, descricao, duracaoMinutos);
        eventos.add(evento);
        return evento;
    }

    public Evento cadastrarOnline(String nome, String plataforma, String link, Categoria categoria,
                                  LocalDateTime horario, String descricao, int duracaoMinutos) {
        Evento evento = new EventoOnline(proximoId++, nome, plataforma, link, categoria,
                horario, descricao, duracaoMinutos);
        eventos.add(evento);
        return evento;
    }

    // devolve null se não encontrar
    public Evento buscarPorId(int id) {
        for (Evento evento : eventos) {
            if (evento.getId() == id) {
                return evento;
            }
        }
        return null;
    }

    public List<Evento> listar() {
        return List.copyOf(eventos);
    }

    // do mais próximo (ou mais antigo) para o mais distante
    public List<Evento> listarOrdenados() {
        return eventos.stream().sorted(POR_HORARIO).toList();
    }

    public List<Evento> emAndamento() {
        return eventos.stream()
                .filter(Evento::estaAcontecendo)
                .sorted(POR_HORARIO)
                .toList();
    }

    public List<Evento> proximos() {
        return eventos.stream()
                .filter(Evento::aindaVaiAcontecer)
                .sorted(POR_HORARIO)
                .toList();
    }

    // os mais recentes aparecem primeiro
    public List<Evento> encerrados() {
        return eventos.stream()
                .filter(Evento::jaTerminou)
                .sorted(POR_HORARIO.reversed())
                .toList();
    }

    public List<Evento> buscarPorCategoria(Categoria categoria) {
        return eventos.stream()
                .filter(evento -> evento.getCategoria() == categoria)
                .sorted(POR_HORARIO)
                .toList();
    }

    // eventos em andamento que interessam ao usuário
    public List<Evento> acontecendoAgora(Usuario usuario) {
        return emAndamento().stream()
                .filter(evento -> usuario.temInteresse(evento.getCategoria()))
                .toList();
    }
}
