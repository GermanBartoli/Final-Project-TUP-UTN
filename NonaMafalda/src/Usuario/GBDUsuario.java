package Usuario;

import Usuario.CUsuario;
import Modelos.GestorBDConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GBDUsuario {

    GestorBDConexion gestorBDConexion = new GestorBDConexion();

    //Para logearse, esta en el login, btnIngresar
    public boolean ExisteNombreContraseñaUsuario(CUsuario usuario) throws SQLException {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();

            String sql
                    = "select * "
                    + "from Usuario "
                    + "WHERE nombre = ? "
                    + "AND contrasena = ? "
                    + "and true_false = 1";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getContraseña());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                existe = true;
            }
            rs.close();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return existe;
    }

    //esta en el login, btnIngresar, lo uso para obtener todos los atributos y pasarlo al menu principal
    //O se puede obtener partes del CUsuario
    public CUsuario ObtenerUsuarioXNombre(CUsuario usuario) throws SQLException {
        CUsuario u = null;

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "SELECT * "
                    + "FROM Usuario "
                    + "WHERE  Nombre = ? "
                    + "and True_False = 1";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);
            stmt.setString(1, usuario.getNombre());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idUsuario = rs.getInt("ID_Usuario");
                int idEmpleado = rs.getInt("ID_Empleado");
                String nombre = rs.getString("Nombre");
                String contraseña = rs.getString("Contrasena");

                u = new CUsuario(idUsuario, idEmpleado, nombre, contraseña, true);
            }

            rs.close();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return u;
    }

    //Para saber si se equivoco en el nombre o la contra al logear, lo uso en el login, btnIngresar
    //Hacer nuevo abm
    public boolean ExisteUsuarioNombre(CUsuario usuario) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Usuario "
                    + "WHERE nombre = ? "
                    + "and True_False = 1";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setString(1, usuario.getNombre());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                existe = true;
            }

            rs.close();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return existe;
    }

    public boolean ModificarConreseña(CUsuario usuario) throws SQLException {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();

            String sql = "UPDATE Usuario "
                    + "SET contrasena=? "
                    + "where ID_Usuario=?";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setString(1, usuario.getContraseña());
            stmt.setInt(2, usuario.getIdUsuario());

            filasAfectadas = stmt.executeUpdate();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        if (filasAfectadas > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ModificarNombre(CUsuario usuario) throws SQLException {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();

            String sql = "UPDATE Usuario "
                    + "SET nombre=? "
                    + "where ID_Usuario=?";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setString(1, usuario.getNombre());
            stmt.setInt(2, usuario.getIdUsuario());

            filasAfectadas = stmt.executeUpdate();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        if (filasAfectadas > 0) {
            return true;
        } else {
            return false;
        }
    }
}
