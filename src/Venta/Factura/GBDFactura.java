/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venta.Factura;

import Venta.Factura.CFactura;
import Modelos.GestorBDConexion;
import Stock.CStock;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;

/**
 *
 * @author German Bartoli
 */
public class GBDFactura {

    GestorBDConexion gestorBDConexion = new GestorBDConexion();

    ArrayList<CFactura> listadoFacturas;

    public ArrayList<CFactura> CargarTablaFacturasXIDCliente(int idClienteSeleccionado) {
        listadoFacturas = new ArrayList<>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "select "
                    + "ID_Factura, "
                    + "ID_Empleado, "
                    + "ID_Cliente, "
                    + "ID_Forma_De_Pago, "
                    + "Fecha, "
                    + "hora, "
                    + "Precio_Final, "
                    + "Pago_Cliente, "
                    + "Vuelto, "
                    + "Baja_Logica "
                    + "From Factura "
                    + "where ID_Cliente = ? "
                    + "Order by 5 desc ,6 desc";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);
            stmt.setInt(1, idClienteSeleccionado);
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int ID_Factura = rs.getInt(1);
                int ID_Empleado = rs.getInt(2);
                int ID_Cliente = rs.getInt(3);
                int ID_Forma_De_Pago = rs.getInt(4);
                Date Fecha = rs.getDate(5);
                Time hora = rs.getTime(6);
                double precioFinal = rs.getDouble(7);
                double Pago_Cliente = rs.getDouble(8);
                double Vuelto = rs.getDouble(9);
                boolean bajaLogica = rs.getBoolean(10);

                CFactura cFactura = new CFactura(ID_Factura, ID_Empleado, ID_Cliente, ID_Forma_De_Pago,
                        Fecha, hora, precioFinal, Pago_Cliente, Vuelto, bajaLogica);

                listadoFacturas.add(cFactura);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return listadoFacturas;
    }

    public int ObtenerUltimoIDFactura() {
        int ultimoIDPersona = 0;
        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "SELECT "
                    + "MAX(ID_Factura) "
                    + "FROM Factura;";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ultimoIDPersona = rs.getInt(1);
            }
            rs.close();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return ultimoIDPersona;
    }

    public boolean AgregarFactura(CFactura factura) {
        boolean b = false;
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "INSERT INTO Factura("
                    + "ID_Empleado, "
                    + "ID_Cliente, "
                    + "ID_Forma_De_Pago, "
                    + "Fecha, "
                    + "hora, "
                    + "Precio_Final, "
                    + "Pago_Cliente, "
                    + "Vuelto, "
                    + "Baja_Logica) "
                    + "VALUES (?,?,?,?,?,?,?,?,?)"
            );
            stmt.setInt(1, factura.getID_Empleado());
            stmt.setInt(2, factura.getID_Cliente());
            stmt.setInt(3, factura.getID_Forma_De_Pago());

            java.util.Date dateFecha = factura.getFecha();
            java.sql.Date sqlDateFecha = new java.sql.Date(dateFecha.getTime());
            stmt.setDate(4, sqlDateFecha);

            java.sql.Time timeHora = factura.getHora();
            java.sql.Time sqlTimeHora = new java.sql.Time(timeHora.getTime());
            stmt.setTime(5, sqlTimeHora);

            stmt.setDouble(6, factura.getPrecio_Final());
            stmt.setDouble(7, factura.getPago_Cliente());
            stmt.setDouble(8, factura.getVuelto());

            stmt.setBoolean(9, true);

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
