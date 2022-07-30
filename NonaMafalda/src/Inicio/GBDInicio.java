/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inicio;

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
public class GBDInicio {

    ArrayList<DTO> dTOProductos;

    GestorBDConexion gestorBDConexion = new GestorBDConexion();

    public ArrayList<DTO> CargarTablaDTOProductosStockXVencer() {
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
                    + "DATEDIFF(day, GETDATE(), stock.Fecha_Vencimiento) "
                    + "From Producto p "
                    + "inner join Marca m on m.Id_Marca = p.Id_Marca "
                    + "inner join Tipo_Producto tp on tp.ID_Tipo_Producto = p.ID_Tipo_Producto "
                    + "inner join Stock stock on stock.id_Producto = p.Id_Producto "
                    + "where p.True_False = 1 "
                    + "and stock.True_False = 1 "
                    + "and (DATEDIFF(day, GETDATE(), stock.Fecha_Vencimiento) >= 0) "
                    + "and (DATEDIFF(day, GETDATE(), stock.Fecha_Vencimiento) <= 5) "
                    + "Order By 7";
//                  + "and (stock.Fecha_Vencimiento BETWEEN (DATEADD(day, 5, GETDATE())) AND  GETDATE()) "

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
                int diasParaVencer = rs.getInt(7);

                DTO dTOProducto = new DTO();

                dTOProducto.setNombreProducto(nombreProducto);
                dTOProducto.setMarca(marca);
                dTOProducto.setTipoProducto(tipoProducto);
                dTOProducto.setCantidadStock(cantidad);
                dTOProducto.setFechaElaboracion(fechaElaboracion);
                dTOProducto.setFechaVencimiento(fechaVencimiento);
                dTOProducto.setDiasParaVencer(diasParaVencer);

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

    public ArrayList<DTO> CargarTablaDTOProductosStockBajo() {
        dTOProductos = new ArrayList<DTO>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "select "
                    + "p.Nombre, "
                    + "m.marca, "
                    + "tp.Tipo_Producto, "
                    + "p.stock_minimo, "
                    + "stock.cantidad "
                    + "From Producto p "
                    + "inner join Marca m on m.Id_Marca = p.Id_Marca "
                    + "inner join Tipo_Producto tp on tp.ID_Tipo_Producto = p.ID_Tipo_Producto "
                    + "inner join Stock stock on stock.id_Producto = p.Id_Producto "
                    + "where p.True_False = 1 "
                    + "and stock.True_False = 1 "
                    + "and  0 <= stock.cantidad "
                    + "and stock.cantidad <= p.stock_minimo  "
                    + "Order By stock.cantidad";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // int idStock = rs.getInt(ID_Stock);
                String nombreProducto = rs.getString(1);
                String marca = rs.getString(2);
                String tipoProducto = rs.getString(3);
                int stockMinimo = rs.getInt(4);
                double cantidad = rs.getDouble(5);

                DTO dTOProducto = new DTO();

                dTOProducto.setNombreProducto(nombreProducto);
                dTOProducto.setMarca(marca);
                dTOProducto.setTipoProducto(tipoProducto);
                dTOProducto.setStockMinimo(stockMinimo);
                dTOProducto.setCantidadStock(cantidad);

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
