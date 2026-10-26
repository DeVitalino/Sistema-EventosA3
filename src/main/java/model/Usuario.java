package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Usuario {

    private final int id;
    private final String nome;
    private final String email;
    private final String telefone;
    private final String bairro;
    private final List<Categoria> categoriasInteresse = new ArrayList<>();
    private final List<Evento> participacoes = new ArrayList<>();

    public Usuario(int id, String nome, String email, String telefone, String bairro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.bairro = bairro;
    }

    // retorna false se a categoria já estava na lista
    public boolean adicionarInteresse(Categoria categoria) {
        if (categoriasInteresse.contains(categoria)) {
            return false;
        }
        categoriasInteresse.add(categoria);
        return true;
    }

    // quem não escolheu nenhuma categoria recebe todas
    public boolean temInteresse(Categoria categoria) {
        return categoriasInteresse.isEmpty() || categoriasInteresse.contains(categoria);
    }

    // retorna false se a participação já estava confirmada
    public boolean confirmarParticipacao(Evento evento) {
        if (participacoes.contains(evento)) {
            return false;
        }
        participacoes.add(evento);
        return true;
    }

    // retorna false se o usuário não participava do evento
    public boolean cancelarParticipacao(Evento evento) {
        return participacoes.remove(evento);
    }

    public boolean participaDe(Evento evento) {
        return participacoes.contains(evento);
    }

    // ordenadas pelo horário dos eventos
    public List<Evento> getParticipacoes() {
        return participacoes.stream()
                .sorted(Comparator.comparing(Evento::getHorario))
                .toList();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getBairro() {
        return bairro;
    }

    public List<Categoria> getCategoriasInteresse() {
        return List.copyOf(categoriasInteresse);
    }
}
