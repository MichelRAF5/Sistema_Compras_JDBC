package entities;

import entities.enums.Compras;
import jdbc.ClassConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public final class Carrinho {
    private ArrayList<Produto> itensEscolhidos = new ArrayList<>();
    private double precoCarrinho = 0;
    private double valorFinal = 0;

    public ArrayList<Produto> getItensEscolhidos() {
        return itensEscolhidos;
    }

    public double getPrecoCarrinho() {
        return precoCarrinho;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    // Adiciona o produto selecionado ao carrinho e modifica o preço do carrinho
    public void adicionarAoCarrinho(int numeroProduto) throws SQLException {
        Connection conexao = ClassConnection.getConnection();
        Statement stmt = conexao.createStatement();

        String sql = "select * from produtos where id=" + numeroProduto;
        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next()) {
            Produto produto = new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco"));
            itensEscolhidos.add(produto);
            precoCarrinho += produto.getPrecoProduto();
        }

        rs.close();
        stmt.close();
        conexao.close();
    }

    // Exibe todos os produtos adicionados ao carrinho
    public String exibirCarrinho() {
        String carrinho = "";

        if (itensEscolhidos.isEmpty()) {
            carrinho = Compras.CARRINHO_VAZIO.toString();
        } else {
            for (Produto produto : itensEscolhidos) {
                carrinho += produto;
            }
        }
        return carrinho;
    }

    // Verifica se a compra terá frete grátis (caso preço seja maior que 500
    public boolean verificarFreteGratis() {
        if (precoCarrinho > 500) {
            return true;
        } else
            return false;
    }

    // Aplica o valor do frete caso não seja gratis
    public double precoTotal() {
        if (!verificarFreteGratis()) {
            valorFinal = precoCarrinho + 25;
        } else
            valorFinal = precoCarrinho;

        return valorFinal;
    }

    // Registra a compra no db e altera o estoque do produto comprado
    public boolean finalizarCompra(Cliente cliente, String formaPagamento) throws SQLException {
        boolean compraFinalizada = false;
        Connection conexao = ClassConnection.getConnection();
        Statement stmt = conexao.createStatement();

        for (Produto produto : itensEscolhidos) {
            String addCompra = "insert into compras (id_produto, cpf_cliente, metodo_pagamento) " +
                    "values (" + produto.getId() + ", '" + cliente.getCpf() + "', '" + formaPagamento + "')";

            int linhasAfetadas = stmt.executeUpdate(addCompra);
            if (linhasAfetadas > 0) {
                String reduzirEstoque = "update produtos set qtd_estoque= qtd_estoque-1 where id=" + produto.getId();

                int linhasAfetadas2 = stmt.executeUpdate(reduzirEstoque);
                if (linhasAfetadas2 > 0) {
                    compraFinalizada = true;
                }
            }
        }

        stmt.close();
        conexao.close();

        return compraFinalizada;
    }
}