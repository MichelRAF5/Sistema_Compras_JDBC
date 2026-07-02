package applications;

import entities.Carrinho;
import entities.Cliente;
import entities.Estoque;
import entities.Pagamento;
import entities.enums.Compras;
import entities.enums.Sistema;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private Scanner sc = new Scanner(System.in);
    private Carrinho carrinho = new Carrinho();
    private Pagamento pagamento = new Pagamento();

    // Metodo que exibe o primeiro menu e liga as ações aos outros
    public void exibirMenuPrincipal() throws SQLException {
        char opcao;

        do {
            System.out.println(
                    "=================================" +
                            "\nSelecione a opção que você deseja:" +
                            "\n1- Adicionar produtos ao carrinho" +
                            "\n2- Ver carrinho" +
                            "\n3- Sair" +
                            "\n================================="
            );
            opcao = sc.next().charAt(0);

            if (opcao == '1') {
                System.out.println(Sistema.ABRINDO_COMPRAS);
                menuCompras();
            } else if (opcao == '2') {
                System.out.println(Sistema.ABRINDO_CARRINHO);
                menuCarrinho();
            } else if (opcao == '3') {
                System.out.println(Sistema.SAINDO);
            } else
                System.out.println(Sistema.OPCAO_INVALIDA);

        } while (opcao != '3');

        sc.close();
    }

    // Metodo que exibe os produtos disponíveis para compra e armazena os itens escolhidos na ArrayList da classe Carrinho
    public void menuCompras() throws SQLException {
        Estoque estoque = new Estoque();
        boolean validacao = false; // boolean para validar o try
        do {
            System.out.println(
                    "=================================" +
                            "\nSelecione o número produto que deseja comprar:"
            );
            System.out.print(estoque.exibirEstoque());

            try {
                int prod = sc.nextInt();
                sc.nextLine(); // Limpar buffer
                carrinho.adicionarAoCarrinho(prod);
                validacao = true;
            } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
                System.out.println(Compras.PRODUTO_INVALIDO);
                sc.nextLine(); // Limpar buffer
            }

        } while (!validacao);
    }

    // Metodo que exibe os produtos adicionados ao carrinho e realiza a decisão de finalizar ou não a compra
    public void menuCarrinho() {
        if (carrinho.exibirCarrinho().equals(Compras.CARRINHO_VAZIO.toString())) {
            System.out.println(carrinho.exibirCarrinho());
            return;
        } else {
            System.out.println(
                    "=================================" +
                            "\nCarrinho:"
            );
            System.out.print(carrinho.exibirCarrinho());

            System.out.printf("Preço total: R$ %.2f%n", carrinho.precoTotal());
            System.out.println("=================================");
        }

        char finalizar;
        do {
            System.out.println(
                    "Deseja finalizar a compra?" +
                            "\n1- Sim" +
                            "\n2- Não"
            );
            finalizar = sc.next().charAt(0);

            if (finalizar == '1') {
                coletarDadosCliente();
            } else if (finalizar != '2') {
                System.out.println(Sistema.OPCAO_INVALIDA);
            }

        } while (finalizar != '1' && finalizar != '2');
    }

    // Metodo que coleta e armazena os dados do cliente
    public void coletarDadosCliente() {
        System.out.println("Digite seus dados para finalizar a compra:");
        System.out.println("Nome completo:");
        sc.nextLine(); // Limpar buffer
        String nome = sc.nextLine();

        System.out.println("CPF:");
        String cpf = sc.nextLine();

        System.out.println("Cidade:");
        String cidade = sc.nextLine();

        System.out.println("Estado:");
        String estado = sc.nextLine();

        System.out.println("Bairro:");
        String bairro = sc.nextLine();

        System.out.println("Rua:");
        String rua = sc.nextLine();

        System.out.println("Número:");
        try {
            int numero = sc.nextInt();
            sc.nextLine(); // Limpar buffer
            Cliente cliente = new Cliente(nome, cpf, cidade, estado, bairro, rua, numero);
            cliente.adicionarModificarCliente();
            processarPagamento(cliente);
        } catch (InputMismatchException e) {
            System.out.println(Sistema.OPCAO_INVALIDA);
            sc.nextLine(); // Limpar buffer
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Metodo que processa a forma de pagamento escolhida
    public void processarPagamento(Cliente cliente) throws SQLException {
        char formaPagamento;
        do {
            System.out.println(
                    "=================================" +
                            "\nSelecione a forma de formaPagamento" +
                            "\n1- Cartão de crédito" +
                            "\n2- PIX"
            );
            formaPagamento = sc.next().charAt(0);

            if (formaPagamento != '1' && formaPagamento != '2') {
                System.out.println(Sistema.OPCAO_INVALIDA);
            }

        } while (formaPagamento != '1' && formaPagamento != '2');

        // Cartão de crédito
        if (formaPagamento == '1') {
            System.out.println("Digite o número do cartão de crédito:");
            sc.nextLine(); // Limpar buffer
            String numeroCartao = sc.nextLine();

            if (pagamento.verificarCartao(numeroCartao)) {
                if (carrinho.finalizarCompra(cliente, "Credito")) {
                    exibirCompra("Cartão de crédito");
                } else {
                    System.out.println(Compras.ERRO);
                }
            } else
                System.out.println(Compras.CARTAO_INVALIDO);
        }
        // PIX
        else if (formaPagamento == '2') {
            System.out.println(
                    "Chave de formaPagamento via PIX:" +
                            "\ne8b2a1c4-5d9f-4b7e-a123-bc90d1f56789" +
                            "\nAperte ENTER para confirmar o formaPagamento!"
            );
            sc.nextLine(); // Limpar buffer
            sc.nextLine(); // Espera pressionar ENTER

            if (carrinho.finalizarCompra(cliente, "PIX")) {
                exibirCompra("PIX");
            } else {
                System.out.println(Compras.ERRO);
            }

        }
    }

    // Metodo que exibe um resumo da compra finalizada
    public void exibirCompra(String formaPagamento) {
        System.out.println(
                "=================================" +
                        "\n" + Compras.COMPRA_FINALIZADA +
                        "\nNúmero do pedido: " + pagamento.gerarNumeroPedido() +
                        "\nDetalhes da compra:" +
                        "\n" + carrinho.exibirCarrinho() +
                        "\nPreço total: R$ " + String.format("%.2f%n", carrinho.precoTotal()) +
                        "\nForma de pagamento: " + formaPagamento +
                        "\n================================="
        );
    }
}