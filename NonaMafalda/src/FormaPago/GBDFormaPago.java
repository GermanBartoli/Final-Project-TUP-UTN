/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormaPago;

import Modelos.GestorBDConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author German Bartoli
 */
public class GBDFormaPago {

    GestorBDConexion gestorBDConexion = new GestorBDConexion();

    ArrayList<CFormaPago> alComboFormasPago;
    ArrayList<CFormaPago> alFormasPago;

    public boolean ExisteNombreFormaPagoAgregar(CFormaPago cFormaPago) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Forma_Pago "
                    + "WHERE Forma_Pago = ? "
                    + "and True_False = 1";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setString(1, cFormaPago.getFormaPago());

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

    public boolean ExisteNombreFormaPagoEditar(CFormaPago cFormaPago) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Forma_Pago "
                    + "WHERE Forma_Pago = ? "
                    + "and True_False = 1 "
                    + "and ID_Forma_Pago != ?";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setString(1, cFormaPago.getFormaPago());
            stmt.setInt(2, cFormaPago.getIdFormaPago());

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

    public CFormaPago ObtenerFormaPagoXID(int idFormaPago) {
        CFormaPago formaPago = null;
        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL = "SELECT * "
                    + "FROM Forma_Pago "
                    + "WHERE Id_Forma_Pago = " + idFormaPago;

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("Forma_Pago");

                formaPago = new CFormaPago(idFormaPago, nombre, true);
            }
            rs.close();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return formaPago;
    }

    public ArrayList<CFormaPago> CargarListaFormasPago() {
        alFormasPago = new ArrayList<CFormaPago>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL = "select * from Forma_Pago Where True_False = 1 Order by 2";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idFormaPago = rs.getInt("ID_Forma_Pago");
                String nombre = rs.getString("Forma_Pago");

                CFormaPago formaPago = new CFormaPago(idFormaPago, nombre, true);

                alFormasPago.add(formaPago);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return alFormasPago;
    }

    public ArrayList<CFormaPago> CargarComboFormasPago() {

        alComboFormasPago = new ArrayList<CFormaPago>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL = "select * from Forma_Pago Where True_False = 1";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idFormaPago = rs.getInt("ID_Forma_Pago");
                String nombre = rs.getString("Forma_Pago");
                boolean trueFalse = rs.getBoolean("True_False");

                CFormaPago formaPago = new CFormaPago(idFormaPago, nombre, trueFalse);
                alComboFormasPago.add(formaPago);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return alComboFormasPago;
    }

    public boolean AgregarFormaPago(CFormaPago formaPago) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "INSERT INTO Forma_Pago(Forma_Pago, True_False) VALUES (?,?)");
            stmt.setString(1, formaPago.getFormaPago());
            stmt.setBoolean(2, true);

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

    public boolean ModificarFormaPago(CFormaPago formaPago) throws SQLException {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "UPDATE Forma_Pago SET Forma_Pago = ? where ID_Forma_Pago = ?");
            stmt.setString(1, formaPago.getFormaPago());
            stmt.setInt(2, formaPago.getIdFormaPago());

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

    public boolean EliminarFormaPago(int idFormaPago) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement("Update "
                    + "Forma_Pago Set True_False = 0 "
                    + "WHERE ID_Forma_Pago = ?");
            stmt.setInt(1, idFormaPago);

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
