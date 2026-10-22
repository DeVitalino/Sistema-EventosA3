package service;

import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();
    private int proximoId = 1;

    public Usuario cadastrar(String nome, String email, String telefone, String bairro) {
        Usuario usuario = new Usuario(proximoId++, nome, email, telefone, bairro);
        usuarios.add(usuario);
        return usuario;
    }

    public List<Usuario> listar() {
        return List.copyOf(usuarios);
    }

    // devolve null se não encontrar
    public Usuario buscarPorId(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }
}
