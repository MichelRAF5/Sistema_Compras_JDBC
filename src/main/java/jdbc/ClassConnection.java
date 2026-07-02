package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ClassConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sistema_compras";
        String user = "root";
        String password = "";

        return DriverManager.getConnection(url, user, password);
    }
}
