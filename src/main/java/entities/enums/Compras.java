package entities.enums;

public enum Compras {
    PRODUTO_INVALIDO("Produto inválido!"),
    CARRINHO_VAZIO("O carrinho está vazio!"),
    CARTAO_INVALIDO("Cartão inválido!"),
    COMPRA_FINALIZADA("Compra finalizada!"),
    ERRO("Erro ao finalizar compra.");

    private String retorno;

    Compras(String retorno) {
        this.retorno = retorno;
    }

    @Override
    public String toString() {
        return retorno;
    }
}
