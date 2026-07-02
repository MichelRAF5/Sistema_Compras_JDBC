package entities;

import jdbc.ClassConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class Cliente {
    private String nome;
    private String cpf;
    private String cidade;
    private String estado;
    private String bairro;
    private String rua;
    private int numero;

    public Cliente(String nome, String cpf, String cidade, String estado, String bairro, String rua, int numero) {
        this.nome = nome;
        this.cpf = cpf;
        this.cidade = cidade;
        this.estado = estado;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getBairro() {
        return bairro;
    }

    public String getRua() {
        return rua;
    }

    public int getNumero() {
        return numero;
    }

    /* Se o cpf estiver registrado no sistema faz o set da tabela de endereços para o endereço informado.
    Se o cpf não estiver cadastrado no sistema adiciona o cliente e o endereço. */
    public boolean adicionarModificarCliente() throws SQLException {
        Connection conexao = ClassConnection.getConnection();
        Statement stmt = conexao.createStatement();

        String verificarExistencia = "select * from clientes where cpf='" + cpf + "'";
        ResultSet rs = stmt.executeQuery(verificarExistencia);

        boolean cadastradoAlterado = false;

        // Caso o cpf esteja cadastrado no sistema altera o endereço
        if (rs.next()) {
            String modificarEndereco = "update endereco_clientes set estado='" + estado + "', cidade='" + cidade + "', bairro='" + bairro + "', rua='" + rua + "', numero=" + numero + " where cpf_cliente='" + cpf + "'";
            int linhasAlteradas = stmt.executeUpdate(modificarEndereco);
            if (linhasAlteradas > 0) {
                cadastradoAlterado = true;
            }
        }
        // Realizando cadastro caso o cpf não esteja cadastrado
        else {
            String cadastrarCliente = "insert into clientes (cpf, nome)" +
                    "values ('" + cpf + "', '" + nome + "')";

            int linhasAlteradas = stmt.executeUpdate(cadastrarCliente);
            if (linhasAlteradas > 0) {
                String cadastrarEndereco = "insert into endereco_clientes (cpf_cliente, estado, cidade, bairro, rua, numero)" +
                        "values ('"+cpf+"', '"+estado+"', '"+cidade+"', '"+bairro+"', '"+rua+"', "+numero+")";

                int linhasAlteradas2 = stmt.executeUpdate(cadastrarEndereco);
                if (linhasAlteradas2 > 0) {
                    cadastradoAlterado = true;
                }
            }

        }

        rs.close();
        stmt.close();
        conexao.close();

        return cadastradoAlterado;
    }
}