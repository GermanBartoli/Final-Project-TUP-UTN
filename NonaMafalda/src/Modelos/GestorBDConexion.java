package Modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestorBDConexion {

    String urlConexion = "jdbc:sqlserver://GERMAN\\SQLEXPRESS:1433;databaseName=NonaMafalda";
    // String urlConexion = "jdbc:sqlserver://172.16.140.13:1433;databaseName=";
    
    private final String user = "Sa";
    // private final String user = "alumno1w1";

    private final String pass = "123";
    //private final String pass = "alumno1w1";
    
    private Connection con;

    public Connection getConexion() {
        return con;
    }

    public GestorBDConexion() {

    }

    public void AbrirConexion() {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");


            con = DriverManager.getConnection(urlConexion, user, pass);
            // con = DriverManager.getConnection(url, "alumno1w1", "alumno1w1");

            System.out.println("Conexión Abierta a la BD");

        } catch (Exception e) {
            System.out.println("Error Abrir la  Conexión ");
        }
    }

    public void CerrarConexion() {
        try {
            con.close();
            System.out.println("Conexión Cerrada a la BD");

        } catch (SQLException e) {
            System.out.println("Error al Cerrar la Conexión");
        }
    }
}
