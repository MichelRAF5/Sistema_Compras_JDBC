package entities;

public final class Produto {
    private int id;
    private String nomeProduto;
    private double precoProduto;
    private int quantidadeEstoque;

    // Contrutores
    public Produto(int id, String nomeProduto, double precoProduto, int quantidadeEstoque) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Produto(int id, String nomeProduto, double precoProduto) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public double getPrecoProduto() {
        return precoProduto;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    @Override
    public String toString() {
        return "Número: " + id + " | Produto: " + nomeProduto + " | preco: " + precoProduto;
    }
}