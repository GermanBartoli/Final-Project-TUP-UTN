/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stock;

import Modelos.GestorBDConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author German Bartoli
 */
public class GBDStock {

    GestorBDConexion gestorBDConexion = new GestorBDConexion();

    ArrayList<CStock> listadoStocks;

    public int ObtenerUltimoIDStock() {
        int ultimoIDStock = 0;
        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "SELECT "
                    + "MAX(ID_Stock) "
                    + "FROM Stock;";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ultimoIDStock = rs.getInt(1);
            }
            rs.close();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return ultimoIDStock;
    }

    public boolean ExisteStockAgregar(CStock cStock) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Stock "
                    + "WHERE ID_Producto = ? "
                    + "and Cantidad = ? "
                    + "and Fecha_Elaboracion = ? "
                    + "and Fecha_Vencimiento = ? "
                    + "and True_False = 1";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setInt(1, cStock.getIdProducto());
            stmt.setDouble(2, cStock.getCantidad());

            java.util.Date dateFechaElaboracion = cStock.getFechaElaboracion();
            java.sql.Date sqlDateFechaElaboracion = new java.sql.Date(dateFechaElaboracion.getTime());
            stmt.setDate(3, sqlDateFechaElaboracion);

            java.util.Date dateFechaVencimiento = cStock.getFechaVencimiento();
            java.sql.Date sqlDateFechaVencimiento = new java.sql.Date(dateFechaVencimiento.getTime());
            stmt.setDate(4, sqlDateFechaVencimiento);

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

    public boolean ExisteStockEditar(CStock cStock) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Stock "
                    + "WHERE ID_Producto = ? "
                    + "and Cantidad = ? "
                    + "and Fecha_Elaboracion = ? "
                    + "and Fecha_Vencimiento = ? "
                    + "and True_False = 1 "
                    + "and ID_Stock != ?";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setInt(1, cStock.getIdProducto());
            stmt.setDouble(2, cStock.getCantidad());

            java.util.Date dateFechaElaboracion = cStock.getFechaElaboracion();
            java.sql.Date sqlDateFechaElaboracion = new java.sql.Date(dateFechaElaboracion.getTime());
            stmt.setDate(3, sqlDateFechaElaboracion);

            java.util.Date dateFechaVencimiento = cStock.getFechaVencimiento();
            java.sql.Date sqlDateFechaVencimiento = new java.sql.Date(dateFechaVencimiento.getTime());
            stmt.setDate(4, sqlDateFechaVencimiento);

            stmt.setDouble(5, cStock.getIdStock());

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

    public ArrayList<CStock> CargarTablaStocks(int idProductoSeleccionado) {
        listadoStocks = new ArrayList<>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "select "
                    + "ID_Stock, "
                    + "ID_Producto, "
                    + "Cantidad, "
                    + "Fecha_Elaboracion, "
                    + "Fecha_Vencimiento from Stock "
                    + "where ID_Producto = " + idProductoSeleccionado + " AND "
                    + "True_False = 1 "
                    + "Order By 3 desc";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // int idStock = rs.getInt("ID_Stock");
                int idStock = rs.getInt(1);
                int idProducto = rs.getInt(2);
                double cantidad = rs.getDouble(3);
                Date fechaElaboracion = rs.getDate(4);
                Date fechaVencimiento = rs.getDate(5);

                CStock stock = new CStock(idStock, idProducto, cantidad, fechaElaboracion, fechaVencimiento, true);

                listadoStocks.add(stock);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return listadoStocks;
    }

    public boolean AgregarStock(CStock stock) {
        boolean b = false;
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement("INSERT INTO Stock("
                    + "ID_Producto, "
                    + "Cantidad, "
                    + "Fecha_Elaboracion, "
                    + "Fecha_Vencimiento, "
                    + "True_False)"
                    + "VALUES (?,?,?,?,?)");
            
            stmt.setInt(1, stock.getIdProducto());
            
            stmt.setDouble(2, stock.getCantidad());

            java.util.Date dateFechaElaboracion = stock.getFechaElaboracion();
            java.sql.Date sqlDateFechaElaboracion = new java.sql.Date(dateFechaElaboracion.getTime());
            stmt.setDate(3, sqlDateFechaElaboracion);

            java.util.Date dateFechaVencimiento = stock.getFechaVencimiento();
            java.sql.Date sqlDateFechaVencimiento = new java.sql.Date(dateFechaVencimiento.getTime());
            stmt.setDate(4, sqlDateFechaVencimiento);

            stmt.setBoolean(5, true);

            filasAfectadas = stmt.executeUpdate();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        if (filasAfectadas > 0) {
            return b = true;
        } else {
            return b;
        }

    }

    public boolean ModificarStock(CStock stock) throws SQLException {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "UPDATE Stock "
                    + "SET ID_Producto = ?, "
                    + "Cantidad = ?, "
                    + "Fecha_Elaboracion = ?, "
                    + "Fecha_Vencimiento = ? "
                    + "where ID_Stock = ?");

            stmt.setInt(1, stock.getIdProducto());
            stmt.setDouble(2, stock.getCantidad());

            java.util.Date dateFechaElaboracion = stock.getFechaElaboracion();
            java.sql.Date sqlDateFechaElaboracion = new java.sql.Date(dateFechaElaboracion.getTime());
            stmt.setDate(3, sqlDateFechaElaboracion);

            java.util.Date dateFechaVencimiento = stock.getFechaVencimiento();
            java.sql.Date sqlDateFechaVencimiento = new java.sql.Date(dateFechaVencimiento.getTime());
            stmt.setDate(4, sqlDateFechaVencimiento);

            stmt.setInt(5, stock.getIdStock());

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

    public boolean EliminarStock(int idStock) throws SQLException {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement("UPDATE Stock "
                    + "SET True_False = 0 "
                    + "where ID_Stock = ?");
            stmt.setInt(1, idStock);

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
