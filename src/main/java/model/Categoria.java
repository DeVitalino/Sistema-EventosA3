package model;

public enum Categoria {

    CULTURA(1, "Cultura"),
    ESPORTES(2, "Esportes"),
    MUSICA(3, "Música"),
    EDUCACAO(4, "Educação"),
    GASTRONOMIA(5, "Gastronomia"),
    TECNOLOGIA(6, "Tecnologia"),
    LAZER(7, "Lazer"),
    OUTROS(8, "Outros");

    private final int codigo;
    private final String descricao;

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

    // devolve null se o código não existir
    public static Categoria porCodigo(int codigo) {
        for (Categoria categoria : values()) {
            if (categoria.codigo == codigo) {
                return categoria;
            }
        }
        return null;
    }
}
