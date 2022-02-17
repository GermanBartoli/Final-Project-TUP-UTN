/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Consultas;

import DTO.DTO;
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
public class GBDConsultas {

    ArrayList<DTO> dTOProductos;

    GestorBDConexion gestorBDConexion = new GestorBDConexion();

    public ArrayList<DTO> CargarTablaDTOProductosSinStock() {
        dTOProductos = new ArrayList<DTO>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "select "
                    + "p.ID_Producto, "
                    + "p.Nombre, "
                    + "m.marca, "
                    + "tp.Tipo_Producto, "
                    + "p.stock_minimo "
                    + "From Producto p "
                    + "inner join Marca m on m.Id_Marca = p.Id_Marca "
                    + "inner join Tipo_Producto tp on tp.ID_Tipo_Producto = p.ID_Tipo_Producto "
                    + "inner join Stock stock on stock.id_Producto = p.Id_Producto "
                    + "where stock.True_False = all "
                    + "( "
                    + "select "
                    + "a.True_False "
                    + "from Producto pr "
                    + "inner join Stock a on a.id_Producto = pr.Id_Producto "
                    + "where pr.ID_Producto = p.ID_Producto "
                    + ") "
                    + "GROUP BY p.ID_Producto, p.Nombre, m.Marca, tp.Tipo_Producto, p.Stock_Minimo "
                    + "union "
                    + "select "
                    + "p.ID_Producto, "
                    + "p.Nombre, "
                    + "m.marca, "
                    + "tp.Tipo_Producto, "
                    + "p.stock_minimo "
                    + "From Producto p "
                    + "inner join Marca m on m.Id_Marca = p.Id_Marca "
                    + "inner join Tipo_Producto tp on tp.ID_Tipo_Producto = p.ID_Tipo_Producto "
                    + "left join Stock stock on stock.id_Producto = p.Id_Producto "
                    + "where "
                    + "p.True_False = 1 "
                    + "and stock.ID_Stock is null "
                    + "GROUP BY p.ID_Producto, p.Nombre, m.Marca, tp.Tipo_Producto, p.Stock_Minimo "
                    + "Order By 2";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // int idStock = rs.getInt(ID_Stock);
                int productoId = rs.getInt(1);
                String nombreProducto = rs.getString(2);
                String marca = rs.getString(3);
                String tipoProducto = rs.getString(4);
                int stockMinimo = rs.getInt(5);

                DTO dTOProducto = new DTO();

                dTOProducto.setIdProducto(productoId);
                dTOProducto.setNombreProducto(nombreProducto);
                dTOProducto.setMarca(marca);
                dTOProducto.setTipoProducto(tipoProducto);
                dTOProducto.setStockMinimo(stockMinimo);

                dTOProductos.add(dTOProducto);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }
        return dTOProductos;
    }

    public ArrayList<DTO> CargarTablaDTOProductosStockVencidos() {
        dTOProductos = new ArrayList<DTO>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "select "
                    + "p.Nombre, "
                    + "m.marca, "
                    + "tp.Tipo_Producto, "
                    + "stock.cantidad, "
                    + "stock.Fecha_Elaboracion, "
                    + "stock.Fecha_Vencimiento, "
                    + "DATEDIFF(day, stock.Fecha_Vencimiento, GETDATE()) "
                    + "From Producto p "
                    + "inner join Marca m on m.Id_Marca = p.Id_Marca "
                    + "inner join Tipo_Producto tp on tp.ID_Tipo_Producto = p.ID_Tipo_Producto "
                    + "inner join Stock stock on stock.id_Producto = p.Id_Producto "
                    + "where p.True_False = 1 "
                    + "and stock.True_False = 1 "
                    + "and (DATEDIFF(day, stock.Fecha_Vencimiento, GETDATE()) >= 0) "
                    + "Order By 7";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // int idStock = rs.getInt(ID_Stock);
                String nombreProducto = rs.getString(1);
                String marca = rs.getString(2);
                String tipoProducto = rs.getString(3);
                double cantidad = rs.getDouble(4);
                Date fechaElaboracion = rs.getDate(5);
                Date fechaVencimiento = rs.getDate(6);
                int diasVencido = rs.getInt(7);

                DTO dTOProducto = new DTO();

                dTOProducto.setNombreProducto(nombreProducto);
                dTOProducto.setMarca(marca);
                dTOProducto.setTipoProducto(tipoProducto);
                dTOProducto.setCantidadStock(cantidad);
                dTOProducto.setFechaElaboracion(fechaElaboracion);
                dTOProducto.setFechaVencimiento(fechaVencimiento);
                dTOProducto.setDiasVencido(diasVencido);

                dTOProductos.add(dTOProducto);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return dTOProductos;
    }

    public ArrayList<DTO> CantidadTotalVendidaXProducto(Date fechaInicio, Date fechaFin) {
        dTOProductos = new ArrayList<DTO>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "SELECT "
                    + "producto.Nombre, "
                    + "SUM(factura_Producto.Cantidad), "
                    + "SUM(factura_Producto.Sub_Total) "
                    + "FROM "
                    + "Factura factura "
                    + "INNER JOIN Detalle_Factura factura_Producto ON (factura_Producto.ID_Factura = factura.ID_Factura) "
                    + "INNER JOIN Producto producto ON (producto.ID_Producto = factura_Producto.ID_Producto) "
                    + "WHERE "
                    + "factura.fecha BETWEEN ? AND ? "
                    + "GROUP BY "
                    + "producto.Nombre "
                    + "ORDER BY "
                    + "2 DESC;";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            java.util.Date dateFechaInicio = fechaInicio;
            java.sql.Date sqlDateFechaInicio = new java.sql.Date(dateFechaInicio.getTime());
            stmt.setDate(1, sqlDateFechaInicio);

            java.util.Date dateFechaFin = fechaFin;
            java.sql.Date sqlDateFechaFin = new java.sql.Date(dateFechaFin.getTime());
            stmt.setDate(2, sqlDateFechaFin);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // int idStock = rs.getInt(ID_Stock);
                String nombreProducto = rs.getString(1);
                Double cantidadDetalleFactura = rs.getDouble(2);
                Double subTotal = rs.getDouble(3);

                DTO dTOProducto = new DTO();

                dTOProducto.setNombreProducto(nombreProducto);
                dTOProducto.setSUMCantidad_Detalle_Factura(cantidadDetalleFactura);
                dTOProducto.setSUMSubtotal(subTotal);

                dTOProductos.add(dTOProducto);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }
        return dTOProductos;
    }

    public ArrayList<DTO> TotalRecaudadoXDias(Date fechaInicio, Date fechaFin) {
        dTOProductos = new ArrayList<DTO>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "SELECT "
                    + "CONVERT(VARCHAR(10), factura.fecha, 111), "
                    + "SUM(factura.Precio_Final) "
                    + "FROM "
                    + "Factura factura "
                    + "WHERE "
                    + "factura.fecha BETWEEN ? AND ? "
                    + "GROUP BY "
                    + "CONVERT(VARCHAR(10), factura.fecha, 111) "
                    + "ORDER BY "
                    + "1 DESC;";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            java.util.Date dateFechaInicio = fechaInicio;
            java.sql.Date sqlDateFechaInicio = new java.sql.Date(dateFechaInicio.getTime());
            stmt.setDate(1, sqlDateFechaInicio);

            java.util.Date dateFechaFin = fechaFin;
            java.sql.Date sqlDateFechaFin = new java.sql.Date(dateFechaFin.getTime());
            stmt.setDate(2, sqlDateFechaFin);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // int idStock = rs.getInt(ID_Stock);
                String fechaFactura = rs.getString(1);
                Double sumPrecioFinal = rs.getDouble(2);

                DTO dTOProducto = new DTO();

                dTOProducto.setFechaFactura(fechaFactura);
                dTOProducto.setSUMprecioFinalFactura(sumPrecioFinal);

                dTOProductos.add(dTOProducto);
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }
        return dTOProductos;
    }

}
