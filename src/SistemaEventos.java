import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Sistema de Cadastro e Notificação de Eventos - Porto Alegre
 *
 * Projeto desenvolvido em Java para execução no Console.
 *
 * Funcionalidades:
 * - Cadastro de usuários
 * - Cadastro de eventos
 * - Listagem de usuários
 * - Listagem de eventos
 * - Busca de eventos por categoria
 * - Notificação de eventos acontecendo no momento
 * - Categorias de eventos previamente definidas
 */
public class SistemaEventos {

    // Scanner utilizado para receber informações digitadas pelo usuário
    private static final Scanner scanner = new Scanner(System.in);

    // Listas que armazenam os usuários e eventos cadastrados
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static final List<Evento> eventos = new ArrayList<>();

    // Contadores utilizados para gerar IDs automaticamente
    private static int proximoIdUsuario = 1;
    private static int proximoIdEvento = 1;

    // Formato utilizado para exibir e ler datas
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Método principal do programa.
     */
    public static void main(String[] args) {

        int opcao;

        System.out.println("==============================================");
        System.out.println("       SISTEMA DE EVENTOS - PORTO ALEGRE");
        System.out.println("==============================================");

        do {

            exibirMenu();

            opcao = lerInteiro("Digite uma opção: ");

            switch (opcao) {

                case 1:
                    cadastrarUsuario();
                    break;

                case 2:
                    cadastrarEvento();
                    break;

                case 3:
                    listarUsuarios();
                    break;

                case 4:
                    listarEventos();
                    break;

                case 5:
                    buscarEventosPorCategoria();
                    break;

                case 6:
                    exibirNotificacoes();
                    break;

                case 7:
                    System.out.println("\nPrograma encerrado.");
                    System.out.println("Obrigado por utilizar o sistema!");

                    break;

                default:
                    System.out.println("\nOpção inválida!");
                    System.out.println("Escolha uma opção entre 1 e 7.");
            }

        } while (opcao != 7);

        scanner.close();
    }

    /**
     * Exibe o menu principal do sistema.
     */
    private static void exibirMenu() {

        System.out.println("\n==============================================");
        System.out.println("                 MENU PRINCIPAL");
        System.out.println("==============================================");
        System.out.println("1 - Cadastrar usuário");
        System.out.println("2 - Cadastrar evento");
        System.out.println("3 - Listar usuários");
        System.out.println("4 - Listar eventos");
        System.out.println("5 - Buscar eventos por categoria");
        System.out.println("6 - Ver notificações");
        System.out.println("7 - Sair");
        System.out.println("==============================================");
    }

    /**
     * Realiza o cadastro de um novo usuário.
     *
     * O usuário possui mais de 3 atributos:
     * - ID
     * - Nome
     * - E-mail
     * - Telefone
     * - Bairro
     * - Categorias de interesse
     */
    private static void cadastrarUsuario() {

        System.out.println("\n==============================================");
        System.out.println("              CADASTRO DE USUÁRIO");
        System.out.println("==============================================");

        String nome = lerTextoObrigatorio("Nome: ");

        String email;

        do {
            email = lerTextoObrigatorio("E-mail: ");

            if (!email.contains("@")) {
                System.out.println("E-mail inválido. Digite novamente.");
            }

        } while (!email.contains("@"));

        String telefone = lerTextoObrigatorio("Telefone: ");

        String bairro = lerTextoObrigatorio("Bairro: ");

        // Cria o usuário
        Usuario usuario = new Usuario(
                proximoIdUsuario,
                nome,
                email,
                telefone,
                bairro
        );

        // Permite que o usuário escolha categorias de interesse
        cadastrarInteresses(usuario);

        // Adiciona o usuário à lista
        usuarios.add(usuario);

        proximoIdUsuario++;

        System.out.println("\nUsuário cadastrado com sucesso!");
        System.out.println("ID do usuário: " + usuario.getId());
    }

    /**
     * Permite ao usuário escolher categorias de interesse.
     *
     * Essas categorias serão utilizadas pelo sistema para
     * filtrar as notificações.
     */
    private static void cadastrarInteresses(Usuario usuario) {

        System.out.println("\nEscolha as categorias de seu interesse.");
        System.out.println("Você pode escolher várias.");
        System.out.println("Digite 0 quando terminar.");

        while (true) {

            exibirCategorias();

            int opcao = lerInteiro("Categoria de interesse: ");

            if (opcao == 0) {
                break;
            }

            Categoria categoria = obterCategoria(opcao);

            if (categoria == null) {
                System.out.println("Categoria inválida.");
            } else if (usuario.getCategoriasInteresse().contains(categoria)) {
                System.out.println("Essa categoria já foi adicionada.");
            } else {
                usuario.getCategoriasInteresse().add(categoria);

                System.out.println(
                        "Categoria adicionada: " + categoria.getDescricao()
                );
            }
        }
    }

    /**
     * Realiza o cadastro de um evento.
     *
     * Todo evento obrigatoriamente possui:
     * - Nome
     * - Endereço
     * - Categoria
     * - Horário
     * - Descrição
     *
     * Além disso, foi adicionada a duração do evento para que
     * o sistema consiga identificar quando ele está acontecendo.
     */
    private static void cadastrarEvento() {

        System.out.println("\n==============================================");
        System.out.println("                CADASTRO DE EVENTO");
        System.out.println("==============================================");

        String nome = lerTextoObrigatorio("Nome do evento: ");

        String endereco = lerTextoObrigatorio(
                "Endereço do evento em Porto Alegre: "
        );

        System.out.println("\nEscolha a categoria do evento:");

        exibirCategorias();

        int opcaoCategoria;

        Categoria categoria;

        do {

            opcaoCategoria = lerInteiro("Categoria: ");

            categoria = obterCategoria(opcaoCategoria);

            if (categoria == null) {
                System.out.println("Categoria inválida.");
            }

        } while (categoria == null);

        LocalDateTime horario = lerDataHora();

        String descricao = lerTextoObrigatorio("Descrição do evento: ");

        int duracao;

        do {

            duracao = lerInteiro(
                    "Duração do evento em minutos: "
            );

            if (duracao <= 0) {
                System.out.println(
                        "A duração deve ser maior que zero."
                );
            }

        } while (duracao <= 0);

        /*
         * A cidade é definida automaticamente como Porto Alegre,
         * pois essa é a finalidade do sistema.
         */
        String cidade = "Porto Alegre";

        Evento evento = new Evento(
                proximoIdEvento,
                nome,
                endereco,
                cidade,
                categoria,
                horario,
                descricao,
                duracao
        );

        eventos.add(evento);

        proximoIdEvento++;

        System.out.println("\nEvento cadastrado com sucesso!");
        System.out.println("ID do evento: " + evento.getId());
        System.out.println("Cidade: " + evento.getCidade());
    }

    /**
     * Lista todos os usuários cadastrados.
     */
    private static void listarUsuarios() {

        System.out.println("\n==============================================");
        System.out.println("             USUÁRIOS CADASTRADOS");
        System.out.println("==============================================");

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        for (Usuario usuario : usuarios) {

            System.out.println("\nID: " + usuario.getId());
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("E-mail: " + usuario.getEmail());
            System.out.println("Telefone: " + usuario.getTelefone());
            System.out.println("Bairro: " + usuario.getBairro());

            System.out.print("Interesses: ");

            if (usuario.getCategoriasInteresse().isEmpty()) {
                System.out.println("Nenhuma categoria selecionada.");
            } else {

                for (Categoria categoria :
                        usuario.getCategoriasInteresse()) {

                    System.out.print(
                            categoria.getDescricao() + " | "
                    );
                }

                System.out.println();
            }
        }
    }

    /**
     * Lista todos os eventos cadastrados.
     */
    private static void listarEventos() {

        System.out.println("\n==============================================");
        System.out.println("              EVENTOS CADASTRADOS");
        System.out.println("==============================================");

        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return;
        }

        for (Evento evento : eventos) {

            exibirDetalhesEvento(evento);
        }
    }

    /**
     * Exibe os dados completos de um evento.
     */
    private static void exibirDetalhesEvento(Evento evento) {

        System.out.println("\n----------------------------------------------");
        System.out.println("ID: " + evento.getId());
        System.out.println("Nome: " + evento.getNome());
        System.out.println("Endereço: " + evento.getEndereco());
        System.out.println("Cidade: " + evento.getCidade());
        System.out.println(
                "Categoria: " +
                        evento.getCategoria().getDescricao()
        );
        System.out.println(
                "Horário: " +
                        evento.getHorario().format(FORMATO_DATA)
        );
        System.out.println("Duração: " + evento.getDuracaoMinutos() + " minutos");
        System.out.println("Descrição: " + evento.getDescricao());

        if (evento.estaAcontecendo()) {
            System.out.println("STATUS: EVENTO ACONTECENDO AGORA!");
        } else if (evento.jaTerminou()) {
            System.out.println("STATUS: Evento encerrado.");
        } else {
            System.out.println("STATUS: Evento futuro.");
        }

        System.out.println("----------------------------------------------");
    }

    /**
     * Permite pesquisar eventos através de uma categoria.
     */
    private static void buscarEventosPorCategoria() {

        System.out.println("\n==============================================");
        System.out.println("          BUSCAR EVENTOS POR CATEGORIA");
        System.out.println("==============================================");

        exibirCategorias();

        int opcao = lerInteiro("Digite a categoria desejada: ");

        Categoria categoria = obterCategoria(opcao);

        if (categoria == null) {
            System.out.println("Categoria inválida.");
            return;
        }

        boolean encontrou = false;

        System.out.println(
                "\nEventos da categoria: " +
                        categoria.getDescricao()
        );

        for (Evento evento : eventos) {

            if (evento.getCategoria() == categoria) {

                exibirDetalhesEvento(evento);

                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println(
                    "Nenhum evento encontrado nessa categoria."
            );
        }
    }

    /**
     * Exibe as notificações de eventos que estão acontecendo
     * neste momento.
     *
     * O sistema verifica o horário atual e compara com:
     *
     * início do evento <= agora <= fim do evento
     *
     * Caso o usuário tenha categorias de interesse cadastradas,
     * serão exibidos apenas eventos dessas categorias.
     */
    private static void exibirNotificacoes() {

        System.out.println("\n==============================================");
        System.out.println("                NOTIFICAÇÕES");
        System.out.println("==============================================");

        if (usuarios.isEmpty()) {

            System.out.println(
                    "Nenhum usuário cadastrado."
            );

            return;
        }

        int idUsuario = lerInteiro(
                "Digite o ID do usuário: "
        );

        Usuario usuario = buscarUsuarioPorId(idUsuario);

        if (usuario == null) {

            System.out.println(
                    "Usuário não encontrado."
            );

            return;
        }

        System.out.println(
                "\nNotificações para: " +
                        usuario.getNome()
        );

        boolean encontrou = false;

        for (Evento evento : eventos) {

            /*
             * Verifica se o evento está acontecendo agora.
             */
            if (evento.estaAcontecendo()) {

                /*
                 * Se o usuário não escolheu nenhuma categoria,
                 * todos os eventos serão apresentados.
                 *
                 * Caso tenha categorias selecionadas,
                 * somente os eventos de interesse serão exibidos.
                 */
                if (
                        usuario.getCategoriasInteresse().isEmpty()
                                ||
                                usuario.getCategoriasInteresse()
                                        .contains(evento.getCategoria())
                ) {

                    System.out.println("\n🔔 EVENTO ACONTECENDO AGORA!");
                    System.out.println(
                            "Nome: " + evento.getNome()
                    );
                    System.out.println(
                            "Categoria: " +
                                    evento.getCategoria().getDescricao()
                    );
                    System.out.println(
                            "Endereço: " +
                                    evento.getEndereco()
                    );
                    System.out.println(
                            "Cidade: " +
                                    evento.getCidade()
                    );
                    System.out.println(
                            "Horário: " +
                                    evento.getHorario()
                                            .format(FORMATO_DATA)
                    );
                    System.out.println(
                            "Descrição: " +
                                    evento.getDescricao()
                    );

                    encontrou = true;
                }
            }
        }

        if (!encontrou) {

            System.out.println(
                    "\nNenhum evento de interesse está " +
                            "acontecendo neste momento."
            );
        }
    }

    /**
     * Procura um usuário através do ID.
     */
    private static Usuario buscarUsuarioPorId(int id) {

        for (Usuario usuario : usuarios) {

            if (usuario.getId() == id) {
                return usuario;
            }
        }

        return null;
    }

    /**
     * Exibe todas as categorias permitidas pelo sistema.
     *
     * O uso de enum impede que sejam cadastradas categorias
     * diferentes das definidas no projeto.
     */
    private static void exibirCategorias() {

        for (Categoria categoria : Categoria.values()) {

            System.out.println(
                    categoria.getCodigo() +
                            " - " +
                            categoria.getDescricao()
            );
        }

        System.out.println("0 - Finalizar seleção");
    }

    /**
     * Converte o número escolhido pelo usuário em uma categoria.
     */
    private static Categoria obterCategoria(int codigo) {

        for (Categoria categoria : Categoria.values()) {

            if (categoria.getCodigo() == codigo) {
                return categoria;
            }
        }

        return null;
    }

    /**
     * Lê um texto que obrigatoriamente deve ser preenchido.
     */
    private static String lerTextoObrigatorio(String mensagem) {

        String texto;

        do {

            System.out.print(mensagem);

            texto = scanner.nextLine().trim();

            if (texto.isEmpty()) {

                System.out.println(
                        "Este campo é obrigatório."
                );
            }

        } while (texto.isEmpty());

        return texto;
    }

    /**
     * Lê um número inteiro com tratamento de erro.
     */
    private static int lerInteiro(String mensagem) {

        while (true) {

            try {

                System.out.print(mensagem);

                return Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Digite apenas um número inteiro."
                );
            }
        }
    }

    /**
     * Lê e valida a data e horário do evento.
     *
     * Formato esperado:
     *
     * dd/MM/yyyy HH:mm
     *
     * Exemplo:
     * 10/10/2026 19:30
     */
    private static LocalDateTime lerDataHora() {

        while (true) {

            try {

                System.out.print(
                        "Data e horário (dd/MM/yyyy HH:mm): "
                );

                String entrada = scanner.nextLine();

                return LocalDateTime.parse(
                        entrada,
                        FORMATO_DATA
                );

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Data inválida!"
                );

                System.out.println(
                        "Use o formato: dd/MM/yyyy HH:mm"
                );
            }
        }
    }

    // =========================================================
    // CLASSE USUÁRIO
    // =========================================================

    /**
     * Representa um usuário do sistema.
     */
    static class Usuario {

        private int id;
        private String nome;
        private String email;
        private String telefone;
        private String bairro;

        /*
         * Lista de categorias que o usuário deseja receber
         * nas notificações.
         */
        private List<Categoria> categoriasInteresse;

        public Usuario(
                int id,
                String nome,
                String email,
                String telefone,
                String bairro
        ) {

            this.id = id;
            this.nome = nome;
            this.email = email;
            this.telefone = telefone;
            this.bairro = bairro;

            this.categoriasInteresse = new ArrayList<>();
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
            return categoriasInteresse;
        }
    }

    // =========================================================
    // CLASSE EVENTO
    // =========================================================

    /**
     * Representa um evento cadastrado no sistema.
     */
    static class Evento {

        private int id;
        private String nome;
        private String endereco;
        private String cidade;
        private Categoria categoria;
        private LocalDateTime horario;
        private String descricao;

        /*
         * A duração é utilizada para saber se o evento
         * ainda está acontecendo.
         */
        private int duracaoMinutos;

        public Evento(
                int id,
                String nome,
                String endereco,
                String cidade,
                Categoria categoria,
                LocalDateTime horario,
                String descricao,
                int duracaoMinutos
        ) {

            this.id = id;
            this.nome = nome;
            this.endereco = endereco;
            this.cidade = cidade;
            this.categoria = categoria;
            this.horario = horario;
            this.descricao = descricao;
            this.duracaoMinutos = duracaoMinutos;
        }

        public int getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }

        public String getEndereco() {
            return endereco;
        }

        public String getCidade() {
            return cidade;
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

        /**
         * Verifica se o evento está acontecendo agora.
         */
        public boolean estaAcontecendo() {

            LocalDateTime agora = LocalDateTime.now();

            LocalDateTime fim = horario.plusMinutes(
                    duracaoMinutos
            );

            return !agora.isBefore(horario)
                    && !agora.isAfter(fim);
        }

        /**
         * Verifica se o evento já terminou.
         */
        public boolean jaTerminou() {

            LocalDateTime agora = LocalDateTime.now();

            LocalDateTime fim = horario.plusMinutes(
                    duracaoMinutos
            );

            return agora.isAfter(fim);
        }
    }

    // =========================================================
    // ENUM DE CATEGORIAS
    // =========================================================

    /**
     * Categorias permitidas para os eventos.
     *
     * O uso de enum garante que o sistema trabalhe somente
     * com categorias previamente definidas.
     */
    enum Categoria {

        CULTURA(1, "Cultura"),
        ESPORTES(2, "Esportes"),
        MUSICA(3, "Música"),
        EDUCACAO(4, "Educação"),
        GASTRONOMIA(5, "Gastronomia"),
        TECNOLOGIA(6, "Tecnologia"),
        LAZER(7, "Lazer"),
        OUTROS(8, "Outros");

        private int codigo;
        private String descricao;

        Categoria(int codigo, String descricao) {

            this.codigo = codigo;
            this.descricao = descricao;
        }

        public int getCodigo() {
            return codigo;
        }

        public String getDescricao() {
            return descricao;
        }
    }
}