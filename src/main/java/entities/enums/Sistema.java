package entities.enums;

public enum Sistema {
    ABRINDO_COMPRAS("Abrindo o menu de compras..."),
    ABRINDO_CARRINHO("Abrindo o carrinho..."),
    SAINDO("Fechando o programa..."),
    OPCAO_INVALIDA("Opção inválida!");

    private String retorno;

    Sistema(String retorno) {
        this.retorno = retorno;
    }

    @Override
    public String toString() {
        return retorno;
    }
}
