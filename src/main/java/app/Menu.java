package app;

import model.Categoria;
import model.Evento;
import model.Usuario;
import service.EventoService;
import service.UsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Menu {

    private final UsuarioService usuarios = new UsuarioService();
    private final EventoService eventos = new EventoService();

    public void iniciar() {
        System.out.println("=== SISTEMA DE EVENTOS - PORTO ALEGRE ===");

        int opcao;
        do {
            mostrarOpcoes();
            opcao = Entrada.inteiro("Opção: ");

            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> cadastrarEvento();
                case 3 -> listarUsuarios();
                case 4 -> listarEventos();
                case 5 -> buscarPorCategoria();
                case 6 -> verNotificacoes();
                case 7 -> System.out.println("Programa encerrado.");
                default -> System.out.println("Opção inválida. Escolha de 1 a 7.");
            }
        } while (opcao != 7);
    }

    private void mostrarOpcoes() {
        System.out.println("\n--- MENU ---");
        System.out.println("1 - Cadastrar usuário");
        System.out.println("2 - Cadastrar evento");
        System.out.println("3 - Listar usuários");
        System.out.println("4 - Listar eventos");
        System.out.println("5 - Buscar eventos por categoria");
        System.out.println("6 - Ver notificações");
        System.out.println("7 - Sair");
    }

    private void titulo(String texto) {
        System.out.println("\n--- " + texto.toUpperCase() + " ---");
    }

    //parte de usuarios

    private void cadastrarUsuario() {
        titulo("Cadastro de usuário");

        String nome = Entrada.texto("Nome: ");
        String email = lerEmail();
        String telefone = Entrada.texto("Telefone: ");
        String bairro = Entrada.texto("Bairro: ");

        Usuario usuario = usuarios.cadastrar(nome, email, telefone, bairro);
        escolherInteresses(usuario);

        System.out.println("Usuário cadastrado! ID: " + usuario.getId());
    }

    private String lerEmail() {
        String email = Entrada.texto("E-mail: ");
        while (!email.contains("@")) {
            System.out.println("E-mail inválido.");
            email = Entrada.texto("E-mail: ");
        }
        return email;
    }

    private void escolherInteresses(Usuario usuario) {
        System.out.println("\nEscolha suas categorias de interesse.");

        while (true) {
            listarCategorias();
            System.out.println("0 - Terminar");

            int codigo = Entrada.inteiro("Categoria: ");
            if (codigo == 0) {
                break;
            }

            Categoria categoria = Categoria.porCodigo(codigo);
            if (categoria == null) {
                System.out.println("Categoria inválida.");
            } else if (!usuario.adicionarInteresse(categoria)) {
                System.out.println("Essa categoria já foi adicionada.");
            } else {
                System.out.println("Adicionada: " + categoria.getDescricao());
            }
        }
    }

    private void listarUsuarios() {
        titulo("Usuários cadastrados");

        List<Usuario> lista = usuarios.listar();
        if (lista.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        for (Usuario usuario : lista) {
            System.out.println("\nID: " + usuario.getId());
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("E-mail: " + usuario.getEmail());
            System.out.println("Telefone: " + usuario.getTelefone());
            System.out.println("Bairro: " + usuario.getBairro());
            System.out.println("Interesses: " + textoInteresses(usuario));
        }
    }

    private String textoInteresses(Usuario usuario) {
        if (usuario.getCategoriasInteresse().isEmpty()) {
            return "nenhuma categoria selecionada";
        }
        return usuario.getCategoriasInteresse().stream()
                .map(Categoria::getDescricao)
                .collect(Collectors.joining(", "));
    }

    // parte de eventos

    private void cadastrarEvento() {
        titulo("Cadastro de evento");

        String nome = Entrada.texto("Nome do evento: ");
        String endereco = Entrada.texto("Endereço (Porto Alegre): ");
        Categoria categoria = escolherCategoria();
        LocalDateTime horario = Entrada.dataHora("Data e horário (dd/MM/yyyy HH:mm): ");
        String descricao = Entrada.texto("Descrição: ");
        int duracao = lerDuracao();

        Evento evento = eventos.cadastrar(nome, endereco, categoria, horario, descricao, duracao);

        System.out.println("Evento cadastrado! ID: " + evento.getId());
    }

    private int lerDuracao() {
        int duracao = Entrada.inteiro("Duração em minutos: ");
        while (duracao <= 0) {
            System.out.println("A duração deve ser maior que zero.");
            duracao = Entrada.inteiro("Duração em minutos: ");
        }
        return duracao;
    }

    private void listarEventos() {
        titulo("Eventos cadastrados");

        List<Evento> lista = eventos.listar();
        if (lista.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return;
        }

        for (Evento evento : lista) {
            mostrarEvento(evento);
        }
    }

    private void buscarPorCategoria() {
        titulo("Buscar por categoria");

        listarCategorias();
        Categoria categoria = Categoria.porCodigo(Entrada.inteiro("Categoria: "));
        if (categoria == null) {
            System.out.println("Categoria inválida.");
            return;
        }

        List<Evento> encontrados = eventos.buscarPorCategoria(categoria);
        if (encontrados.isEmpty()) {
            System.out.println("Nenhum evento encontrado nessa categoria.");
            return;
        }

        for (Evento evento : encontrados) {
            mostrarEvento(evento);
        }
    }

    private void verNotificacoes() {
        titulo("Notificações");

        if (usuarios.listar().isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        Usuario usuario = usuarios.buscarPorId(Entrada.inteiro("ID do usuário: "));
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        List<Evento> emAndamento = eventos.acontecendoAgora(usuario);
        if (emAndamento.isEmpty()) {
            System.out.println("Nenhum evento de interesse acontecendo agora.");
            return;
        }

        System.out.println("Acontecendo agora para " + usuario.getNome() + ":");
        for (Evento evento : emAndamento) {
            mostrarEvento(evento);
        }
    }

    private void mostrarEvento(Evento evento) {
        System.out.println("\nID: " + evento.getId());
        System.out.println("Nome: " + evento.getNome());
        System.out.println("Endereço: " + evento.getEndereco() + " - " + evento.getCidade());
        System.out.println("Categoria: " + evento.getCategoria().getDescricao());
        System.out.println("Horário: " + evento.getHorario().format(Entrada.FORMATO_DATA));
        System.out.println("Duração: " + evento.getDuracaoMinutos() + " minutos");
        System.out.println("Descrição: " + evento.getDescricao());

        if (evento.estaAcontecendo()) {
            System.out.println("Status: acontecendo agora");
        } else if (evento.jaTerminou()) {
            System.out.println("Status: encerrado");
        } else {
            System.out.println("Status: ainda vai acontecer");
        }
    }

    // parte de categorias

    private void listarCategorias() {
        for (Categoria categoria : Categoria.values()) {
            System.out.println(categoria.getCodigo() + " - " + categoria.getDescricao());
        }
    }

    private Categoria escolherCategoria() {
        listarCategorias();
        Categoria categoria = Categoria.porCodigo(Entrada.inteiro("Categoria: "));
        while (categoria == null) {
            System.out.println("Categoria inválida.");
            categoria = Categoria.porCodigo(Entrada.inteiro("Categoria: "));
        }
        return categoria;
    }
}
