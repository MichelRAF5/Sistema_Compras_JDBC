package entities;

import jdbc.ClassConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Estoque {
    // Exibe os produtos disponíveis em estoque
    public String exibirEstoque() throws SQLException {
        Connection conexao= ClassConnection.getConnection();
        Statement stmt= conexao.createStatement();

        String sql= "select * from produtos where qtd_estoque > 0";
        ResultSet rs= stmt.executeQuery(sql);

        String estoque = "";
        while (rs.next()) {
            Produto prod= new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco"),  rs.getInt("qtd_estoque"));
            estoque += prod + "\n";
        }

        rs.close();
        stmt.close();
        conexao.close();

        return estoque;
    }
}
