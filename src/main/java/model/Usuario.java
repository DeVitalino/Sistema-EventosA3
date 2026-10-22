package model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private final int id;
    private final String nome;
    private final String email;
    private final String telefone;
    private final String bairro;
    private final List<Categoria> categoriasInteresse = new ArrayList<>();

    public Usuario(int id, String nome, String email, String telefone, String bairro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.bairro = bairro;
    }


    public boolean adicionarInteresse(Categoria categoria) {
        if (categoriasInteresse.contains(categoria)) {
            return false;
        }
        categoriasInteresse.add(categoria);
        return true;
    }


    public boolean temInteresse(Categoria categoria) {
        return categoriasInteresse.isEmpty() || categoriasInteresse.contains(categoria);
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
