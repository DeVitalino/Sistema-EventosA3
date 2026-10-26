package app;

import model.Categoria;
import model.Evento;
import model.Usuario;
import service.EventoService;
import service.UsuarioService;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
                case 5 -> consultarPorSituacao();
                case 6 -> buscarPorCategoria();
                case 7 -> verNotificacoes();
                case 8 -> participarDeEvento();
                case 9 -> verParticipacoes();
                case 10 -> System.out.println("Programa encerrado.");
                default -> System.out.println("Opção inválida. Escolha de 1 a 10.");
            }
        } while (opcao != 10);
    }

    private void mostrarOpcoes() {
        System.out.println("\n--- MENU ---");
        System.out.println("1 - Cadastrar usuário");
        System.out.println("2 - Cadastrar evento");
        System.out.println("3 - Listar usuários");
        System.out.println("4 - Listar eventos (por horário)");
        System.out.println("5 - Eventos por situação");
        System.out.println("6 - Buscar eventos por categoria");
        System.out.println("7 - Ver notificações");
        System.out.println("8 - Participar de um evento");
        System.out.println("9 - Minhas participações");
        System.out.println("10 - Sair");
    }

    private void titulo(String texto) {
        System.out.println("\n--- " + texto.toUpperCase() + " ---");
    }

    // ---------- usuários ----------

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

    // pede o ID e devolve o usuário ou null se não der para continuar
    private Usuario pedirUsuario() {
        if (usuarios.listar().isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return null;
        }

        Usuario usuario = usuarios.buscarPorId(Entrada.inteiro("ID do usuário: "));
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
        }
        return usuario;
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

    // ---------- eventos ----------

    private void cadastrarEvento() {
        titulo("Cadastro de evento");

        int tipo = lerTipoEvento();
        String nome = Entrada.texto("Nome do evento: ");
        Categoria categoria = escolherCategoria();
        LocalDateTime horario = Entrada.dataHora("Data e horário (dd/MM/yyyy HH:mm): ");
        String descricao = Entrada.texto("Descrição: ");
        int duracao = lerDuracao();

        Evento evento;
        if (tipo == 1) {
            String endereco = Entrada.texto("Endereço (Porto Alegre): ");
            evento = eventos.cadastrarPresencial(nome, endereco, categoria, horario, descricao, duracao);
        } else {
            String plataforma = Entrada.texto("Plataforma (ex.: Zoom, YouTube): ");
            String link = Entrada.texto("Link de acesso: ");
            evento = eventos.cadastrarOnline(nome, plataforma, link, categoria, horario, descricao, duracao);
        }

        System.out.println("Evento cadastrado! ID: " + evento.getId());
    }

    private int lerTipoEvento() {
        System.out.println("1 - Presencial");
        System.out.println("2 - Online");

        int tipo = Entrada.inteiro("Tipo do evento: ");
        while (tipo != 1 && tipo != 2) {
            System.out.println("Tipo inválido.");
            tipo = Entrada.inteiro("Tipo do evento: ");
        }
        return tipo;
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

        List<Evento> lista = eventos.listarOrdenados();
        if (lista.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return;
        }

        for (Evento evento : lista) {
            evento.exibirDetalhes();
        }
    }

    private void consultarPorSituacao() {
        titulo("Eventos por situação");
        System.out.println("1 - Acontecendo agora");
        System.out.println("2 - Próximos");
        System.out.println("3 - Encerrados");
        System.out.println("4 - Todos, separados por situação");
        System.out.println("0 - Voltar");

        switch (Entrada.inteiro("Opção: ")) {
            case 1 -> mostrarGrupo("Acontecendo agora", eventos.emAndamento());
            case 2 -> mostrarGrupo("Próximos", eventos.proximos());
            case 3 -> mostrarGrupo("Encerrados", eventos.encerrados());
            case 4 -> {
                mostrarGrupo("Acontecendo agora", eventos.emAndamento());
                mostrarGrupo("Próximos", eventos.proximos());
                mostrarGrupo("Encerrados", eventos.encerrados());
            }
            case 0 -> { }
            default -> System.out.println("Opção inválida.");
        }
    }

    private void mostrarGrupo(String titulo, List<Evento> lista) {
        System.out.println("\n=== " + titulo + " (" + lista.size() + ") ===");

        if (lista.isEmpty()) {
            System.out.println("Nenhum evento.");
            return;
        }

        for (Evento evento : lista) {
            evento.exibirDetalhes();
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
            evento.exibirDetalhes();
        }
    }

    private void verNotificacoes() {
        titulo("Notificações");

        Usuario usuario = pedirUsuario();
        if (usuario == null) {
            return;
        }

        List<Evento> emAndamento = eventos.acontecendoAgora(usuario);
        if (emAndamento.isEmpty()) {
            System.out.println("Nenhum evento de interesse acontecendo agora.");
            return;
        }

        System.out.println("Acontecendo agora para " + usuario.getNome() + ":");
        for (Evento evento : emAndamento) {
            evento.exibirDetalhes();
        }
    }

    // ---------- participações ----------

    private void participarDeEvento() {
        titulo("Participar de um evento");

        Usuario usuario = pedirUsuario();
        if (usuario == null) {
            return;
        }

        List<Evento> disponiveis = new ArrayList<>(eventos.emAndamento());
        disponiveis.addAll(eventos.proximos());

        if (disponiveis.isEmpty()) {
            System.out.println("Nenhum evento disponível no momento.");
            return;
        }

        System.out.println("\nEventos disponíveis:");
        for (Evento evento : disponiveis) {
            evento.exibirDetalhes();
        }

        int id = Entrada.inteiro("\nID do evento (0 para voltar): ");
        if (id == 0) {
            return;
        }

        Evento escolhido = eventos.buscarPorId(id);
        if (escolhido == null || escolhido.jaTerminou()) {
            System.out.println("Evento não encontrado ou já encerrado.");
            return;
        }

        if (usuario.participaDe(escolhido)) {
            System.out.println("A participação nesse evento já está confirmada.");
            return;
        }

        if (Entrada.confirmar("Confirmar participação em \"" + escolhido.getNome() + "\"?")) {
            usuario.confirmarParticipacao(escolhido);
            System.out.println("Participação confirmada!");
        } else {
            System.out.println("Participação não confirmada.");
        }
    }

    private void verParticipacoes() {
        titulo("Participações confirmadas");

        Usuario usuario = pedirUsuario();
        if (usuario == null) {
            return;
        }

        List<Evento> lista = usuario.getParticipacoes();
        if (lista.isEmpty()) {
            System.out.println(usuario.getNome() + " ainda não confirmou participação em nenhum evento.");
            return;
        }

        for (Evento evento : lista) {
            evento.exibirDetalhes();
        }

        if (!Entrada.confirmar("\nDeseja cancelar alguma participação?")) {
            return;
        }

        Evento escolhido = eventos.buscarPorId(Entrada.inteiro("ID do evento a cancelar: "));
        if (escolhido == null || !usuario.participaDe(escolhido)) {
            System.out.println("Nenhuma participação confirmada com esse ID.");
            return;
        }

        if (escolhido.jaTerminou()) {
            System.out.println("Esse evento já foi encerrado e não pode ser cancelado.");
            return;
        }

        if (Entrada.confirmar("Cancelar participação em \"" + escolhido.getNome() + "\"?")) {
            usuario.cancelarParticipacao(escolhido);
            System.out.println("Participação cancelada.");
        } else {
            System.out.println("A participação foi mantida.");
        }
    }

    // ---------- categorias ----------

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
