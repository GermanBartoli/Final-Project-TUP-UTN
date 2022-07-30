/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venta.DetalleFactura;

import Venta.DetalleFactura.CDetalleFactura;
import Modelos.GestorBDConexion;
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
public class GBDDetalleFactura {

    GestorBDConexion gestorBDConexion = new GestorBDConexion();

    ArrayList<CDetalleFactura> listadoDetallesFactura;

    public ArrayList<CDetalleFactura> CargarTablaDetallesFacturaXIDFactura(int idFacturaSeleccionada) {
        listadoDetallesFactura = new ArrayList<>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "select "
                    + "ID_Detalle_Factura, "
                    + "ID_Factura, "
                    + "ID_Producto, "
                    //"Producto.*, "
                    + "Cantidad, "
                    + "Precio_Unitario, "
                    + "Sub_Total, "
                    + "Baja_Logica "
                    + "From Detalle_Factura "
                    + "where ID_Factura = ? "
                    + "Order By 6 desc";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);
            stmt.setInt(1, idFacturaSeleccionada);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int ID_Detalle_Factura = rs.getInt(1);
                int ID_Factura = rs.getInt(2);
                int ID_Producto = rs.getInt(3);
                double Cantidad = rs.getDouble(4);
                double Precio_Unitario = rs.getDouble(5);
                double Sub_Total = rs.getDouble(6);
                boolean Baja_Logica = rs.getBoolean(7);

                CDetalleFactura cDetalleFactura = new CDetalleFactura(ID_Detalle_Factura, ID_Factura,
                        ID_Producto, Cantidad, Precio_Unitario, Sub_Total, Baja_Logica);

                listadoDetallesFactura.add(cDetalleFactura);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return listadoDetallesFactura;
    }

    public boolean AgregarDetalleFactura(int ultimoIDFactura, Object[] filaTabla) {
        boolean b = false;
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "INSERT INTO Detalle_Factura"
                    + "("
                    + "ID_Factura, "
                    + "ID_Producto, "
                    + "Cantidad, "
                    + "Precio_Unitario, "
                    + "Sub_Total, "
                    + "Baja_Logica"
                    + ") "
                    + "VALUES (?,?,?,?,?,?)"
            );

            stmt.setInt(1, ultimoIDFactura);

            stmt.setInt(2, (Integer) filaTabla[0]);

            stmt.setDouble(3, (Double) filaTabla[3]);

            stmt.setDouble(4, (Double) filaTabla[4]);

            stmt.setDouble(5, (Double) filaTabla[5]);

            stmt.setBoolean(6, true);

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
