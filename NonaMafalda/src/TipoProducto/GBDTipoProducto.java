/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TipoProducto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Modelos.GestorBDConexion;

/**
 *
 * @author German Bartoli
 */
public class GBDTipoProducto {

    GestorBDConexion gestorBDConexion = new GestorBDConexion();

    ArrayList<CTipoProducto> arrayListTiposProductos;
    ArrayList<CTipoProducto> arrayListComboTiposProductos;

    public boolean ExisteNombreTipoProductoAgregar(CTipoProducto tipoProducto) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Tipo_Producto "
                    + "WHERE Tipo_Producto = ? "
                    + "and True_False = 1";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setString(1, tipoProducto.getTipoProducto());

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
    
    public boolean ExisteNombreTipoProductoEditar(CTipoProducto tipoProducto) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Tipo_Producto "
                    + "WHERE Tipo_Producto = ? "
                    + "and True_False = 1 "
                    + "and ID_Tipo_Producto != ?";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setString(1, tipoProducto.getTipoProducto());
                        stmt.setInt(1, tipoProducto.getIdTipoProducto());

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

    public CTipoProducto ObtenerTipoProducto(int idTipoProducto) {
        CTipoProducto tipoProducto = null;
        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL = "SELECT * FROM Tipo_Producto WHERE Id_Tipo_Producto = " + idTipoProducto;

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("Tipo_Producto");

                tipoProducto = new CTipoProducto(idTipoProducto, nombre, true);
            }
            rs.close();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return tipoProducto;
    }

    public ArrayList<CTipoProducto> CargarListaTiposProductos() {
        arrayListTiposProductos = new ArrayList<CTipoProducto>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL = "select * from Tipo_Producto Where True_False = 1 Order by 2";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idTipoProducto = rs.getInt("ID_Tipo_Producto");
                String nombre = rs.getString("Tipo_Producto");

                CTipoProducto tipoProducto = new CTipoProducto(idTipoProducto, nombre, true);

                arrayListTiposProductos.add(tipoProducto);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return arrayListTiposProductos;
    }

    public ArrayList<CTipoProducto> CargarComboTiposProductos() {

        arrayListComboTiposProductos = new ArrayList<CTipoProducto>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL = "select * "
                    + "from Tipo_Producto "
                    + "where True_False = 1 "
                    + "Order By Tipo_Producto";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idTipoPoroducto = rs.getInt("ID_Tipo_Producto");
                String nombreProducto = rs.getString("Tipo_Producto");
                boolean trueFalse = rs.getBoolean("True_False");

                CTipoProducto tipoProducto = new CTipoProducto(idTipoPoroducto, nombreProducto, trueFalse);
                arrayListComboTiposProductos.add(tipoProducto);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return arrayListComboTiposProductos;
    }

    public boolean AgregarTipoProducto(CTipoProducto tipoProducto) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "INSERT INTO Tipo_Producto(Tipo_Producto, True_False) VALUES (?,?)");
            stmt.setString(1, tipoProducto.getTipoProducto());
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

    public boolean ModificarTipoProducto(CTipoProducto tipoProducto) throws SQLException {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "UPDATE Tipo_Producto SET Tipo_Producto = ? where ID_Tipo_Producto = ?");
            stmt.setString(1, tipoProducto.getTipoProducto());
            stmt.setInt(2, tipoProducto.getIdTipoProducto());

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

    public boolean EliminarTipoProducto(int idTipoProducto) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement("Update "
                    + "Tipo_Producto Set True_False = 0 "
                    + "WHERE ID_Tipo_Producto = ?");
            stmt.setInt(1, idTipoProducto);

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
